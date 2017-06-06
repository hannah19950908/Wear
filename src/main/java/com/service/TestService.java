package com.service;

import com.dao.TestDao;
import com.entity.MeasureEntity;
import com.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by 63289 on 2017/5/11.
 */
@Service
public class TestService {
    private final TestDao testDao;

    @Autowired
    public TestService(TestDao testDao) {
        this.testDao = testDao;
    }

    public void randomGenerate(String accountNumber, Integer number){
        List<MeasureEntity> measureEntities=new ArrayList<>();
        Random random = new Random();
        Integer step = 0;
        Integer distance = 0;
        Integer heart = 40;
        for (int i = 0; i < number; ++i) {
            Long commitTime = System.currentTimeMillis() + i - number;
            String device = "00-00-00-00-00-00-00-00";
            if (i % 100 == 0) {
                Integer change = random.nextInt(2);
                step += change;
                distance += change / (1 + random.nextInt(1));
                heart = 40 + change * 10 + random.nextInt(20);
            } else heart = 40 + random.nextInt(20);
           MeasureEntity measureEntity=new MeasureEntity();
           measureEntity.setAccountNumber(accountNumber);
           measureEntity.setCommitTime(new Timestamp(commitTime));
           measureEntity.setDevice(device);
           measureEntity.setStep(step);
           measureEntity.setDistance(distance);
           measureEntity.setHeart(heart);
           measureEntities.add(measureEntity);
        }
        testDao.addByList(measureEntities);
    }
    public String caclulatePayload(HttpHeaders httpHeaders, String mapString, Object object) throws Exception{
        ByteArrayOutputStream byteArrayOutputStreamOrigin=new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStreamOrigin=new ObjectOutputStream(byteArrayOutputStreamOrigin);
        ByteArrayOutputStream byteArrayOutputStreamGain=new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStreamGain=new ObjectOutputStream(byteArrayOutputStreamGain);

        objectOutputStreamOrigin.writeObject(httpHeaders);

        if(mapString!=null){
            objectOutputStreamOrigin.writeObject(mapString);
        }
        if(object!=null) {
            objectOutputStreamGain.writeObject(object);
        }

        byte[] bytesOrigin=byteArrayOutputStreamOrigin.toByteArray();
        byte[] bytesGain=byteArrayOutputStreamGain.toByteArray();

        Integer originLength=bytesOrigin.length;
        Integer gainLength=bytesGain.length;
        Double payload=gainLength.doubleValue() / originLength.doubleValue();

        Map res=new HashMap();
        res.put("origin",originLength);
        res.put("effective",gainLength);
        res.put("payload",payload);

        return JSONUtils.toJSON(res);
    }
}
