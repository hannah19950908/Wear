package com.service;

import com.Exception.SQLNotFoundException;
import com.dao.MeasureDao;
import com.dao.TokenDao;
import com.entity.MeasureEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 63289 on 2017/4/18.
 */
@Service
public class MeasureService {
    private final MeasureDao measureDao;
    private final TokenDao tokenDao;

    @Autowired
    public MeasureService(MeasureDao measureDao, TokenDao tokenDao) {
        this.measureDao = measureDao;
        this.tokenDao = tokenDao;
    }

    // 查询某账号所有数据
    public String findAllDataByAccountNumber(String accountNumber) throws Exception {
        return measureDao.findByAccountNumber(accountNumber,tokenDao.hasCache(accountNumber));
    }

    //通过起止时间查询某账户某时间段内的数据
    public String findByAccountNumberAndCommitTime(String accountNumber, Timestamp fromTime, Timestamp toTime) throws Exception {
        return measureDao.findByAccountNumberAndTime(accountNumber,tokenDao.hasCache(accountNumber), fromTime, toTime);
    }

    //通过终止时间查询某账户内的数据
    public String findByAccountNumberAndCommitTime(String accountNumber, Timestamp toTime) throws Exception {
        MeasureEntity measureEntity = measureDao.findTheFirstByAccountNumber(accountNumber,tokenDao.hasCache(accountNumber));
        if (measureEntity == null) throw new SQLNotFoundException();
        return findByAccountNumberAndCommitTime(accountNumber, measureEntity.getCommitTime(), toTime);
    }

    //查询某账户从某时间点到现在的所有数据
    public String findByAccountNumberAndFromTime(String accountNumber, Timestamp fromTime) throws Exception {
        return measureDao.findByAccountNumberAndTime(accountNumber,tokenDao.hasCache(accountNumber), fromTime, Timestamp.valueOf(LocalDateTime.now()));
    }

    //查询某账户今日的数据
    public String findTodayDataByAccountNumber(String accountNumber) throws Exception {
        Timestamp fromTime = Timestamp.valueOf(LocalDate.now().atStartOfDay());
        Timestamp toTime = Timestamp.valueOf(LocalDateTime.now());
        return measureDao.findByAccountNumberAndTime(accountNumber,tokenDao.hasCache(accountNumber), fromTime, toTime);
    }

    //查询某账户某日的数据，参数为时间戳
    public String findByAccountNumberAndDate(String accountNumber, Timestamp timestamp) throws Exception {
        LocalDate localDate = timestamp.toLocalDateTime().toLocalDate();
        return findByAccountNumberAndDate(accountNumber, localDate);
    }

    //查询某账户某日的数据，参数为LocalDate
    private String findByAccountNumberAndDate(String accountNumber, LocalDate localDate) throws Exception {
        Timestamp fromTime = Timestamp.valueOf(localDate.atStartOfDay());
        Timestamp toTime = Timestamp.valueOf(localDate.plusDays(1).atStartOfDay());
        return measureDao.findByAccountNumberAndTime(accountNumber,tokenDao.hasCache(accountNumber), fromTime, toTime);
    }

    //查询某账户最新数据
    public MeasureEntity findTheLatestByAccountNumber(String accountNumber) {
        return measureDao.findTheLatestByAccountNumber(accountNumber,tokenDao.hasCache(accountNumber));
    }

    //查询某账户一段日期中每天的最后一条数据，参数为2个时间戳
    public List<MeasureEntity> findTheLatestOfDateByAccountNumberAndDateRange(String accountNumber, Timestamp startTime, Timestamp toTime) throws Exception {
        MeasureEntity measureEntity = measureDao.findTheFirstByAccountNumber(accountNumber,tokenDao.hasCache(accountNumber));
        if (measureEntity == null) throw new SQLNotFoundException();
        Timestamp firstTime = measureEntity.getCommitTime();
        if (firstTime.after(startTime)) startTime = firstTime;
        LocalDate fromLocalDate = startTime.toLocalDateTime().toLocalDate();
        LocalDate toLocalDate = toTime.toLocalDateTime().toLocalDate();
        return findTheLatestOfDateByAccountNumberAndDateRange(accountNumber, fromLocalDate, toLocalDate);
    }

    //查询某账户某个时间前一年中每天的最后一条数据，参数为2个时间戳
    public List<MeasureEntity> findTheLatestOfDateByAccountNumberAndDateRange(String accountNumber, Timestamp toTime) throws Exception {
        MeasureEntity measureEntity = measureDao.findTheFirstByAccountNumber(accountNumber,tokenDao.hasCache(accountNumber));
        if (measureEntity == null) throw new SQLNotFoundException();
        LocalDate fromLocalDate = measureEntity.getCommitTime().toLocalDateTime().toLocalDate();
        LocalDate toLocalDate = toTime.toLocalDateTime().toLocalDate();
        return findTheLatestOfDateByAccountNumberAndDateRange(accountNumber, fromLocalDate, toLocalDate);
    }

    //查询某账户一段日期中每天的最后一条数据，参数为2个LocalDate
    private List<MeasureEntity> findTheLatestOfDateByAccountNumberAndDateRange(String accountNumber, LocalDate fromLocalDate, LocalDate toLocalDate) {
        System.out.println(accountNumber);
        List<MeasureEntity> list = new ArrayList<>();
        for (Long i = new Long(0); i <= toLocalDate.toEpochDay() - fromLocalDate.toEpochDay(); ++i) {
            MeasureEntity measureEntity = measureDao.findTheLatestByAccountNumberAndTime
                    (accountNumber,tokenDao.hasCache(accountNumber), Timestamp.valueOf(fromLocalDate.plusDays(i).atStartOfDay()), Timestamp.valueOf(fromLocalDate.plusDays(i + 1).atStartOfDay()));
            if (measureEntity != null) {
                list.add(measureEntity);
            }
        }
        return list;
    }
    //添加数据
    public void addData(String accountNumber,Long commitTimeMills,String device,Integer step, Integer distance, Integer heart){
        MeasureEntity measureEntity=new MeasureEntity();
        measureEntity.setAccountNumber(accountNumber);
        measureEntity.setCommitTime(new Timestamp(commitTimeMills));
        measureEntity.setDevice(device);
        measureEntity.setStep(step);
        measureEntity.setDistance(distance);
        measureEntity.setHeart(heart);
        measureDao.add(measureEntity);
    }
}
