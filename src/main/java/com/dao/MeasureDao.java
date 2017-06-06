package com.dao;

import com.entity.MeasureEntity;
import com.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 63289 on 2017/4/18.
 */
@Repository
@Transactional
public class MeasureDao {
    private final HibernateTemplate hibernateTemplate;

    @Autowired
    public MeasureDao(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public void add(MeasureEntity measureEntity) {
        hibernateTemplate.save(measureEntity);
    }

    public void addByList(List<MeasureEntity> measureEntities) {
        for (MeasureEntity measureEntity : measureEntities) {
            hibernateTemplate.save(measureEntity);
        }
    }

    public String findByAccountNumber(String accountNumber, Boolean visited) throws Exception {
        String queryString = "from MeasureEntity where accountNumber=? order by commitTime,id";
        Object[] objects = new Object[]{accountNumber};
        return findListByAccountNumberAndQueryStringAndObjects(accountNumber, visited, queryString, objects);
    }

    public String findByAccountNumberAndTime(String accountNumber, Boolean visited, Timestamp fromTime, Timestamp toTime) throws Exception {
        String queryString = "from MeasureEntity where accountNumber=? and commitTime between ? and ? order by commitTime";
        Object[] objects = new Object[]{accountNumber, fromTime, toTime};
        return findListByAccountNumberAndQueryStringAndObjects(accountNumber, visited, queryString, objects);
    }

    public MeasureEntity findTheLatestByAccountNumberAndTime(String accountNumber, Boolean visited, Timestamp fromTime, Timestamp toTime) {
        String queryString = "from MeasureEntity where commitTime in " +
                "(select max(commitTime) from MeasureEntity where accountNumber=? and commitTime between ? and ?)";
        Object[] objects = new Object[]{accountNumber, fromTime, toTime};
        return findByAccountNumberAndQueryStringAndObjects(accountNumber, visited, queryString, objects);
    }

    public MeasureEntity findTheLatestByAccountNumber(String accountNumber, Boolean visited) {
        String queryString = "from MeasureEntity where commitTime in (select max(commitTime) from MeasureEntity where accountNumber=?)";
        Object[] objects = new Object[]{accountNumber};
        return findByAccountNumberAndQueryStringAndObjects(accountNumber, visited, queryString, objects);
    }

    public MeasureEntity findTheFirstByAccountNumber(String accountNumber, Boolean visited) {
        String queryString = "from MeasureEntity where commitTime in (select min(commitTime) from MeasureEntity where accountNumber=?)";
        Object[] objects = new Object[]{accountNumber};
        return findByAccountNumberAndQueryStringAndObjects(accountNumber, visited, queryString, objects);
    }

    private MeasureEntity findByAccountNumberAndQueryStringAndObjects(String accountNumber, Boolean visited, String queryString, Object[] objects) {
        if (visited) {
            Iterator iterator = hibernateTemplate.iterate(queryString, objects);
            return iterator.hasNext() ? (MeasureEntity) iterator.next() : null;
        }
        List list = hibernateTemplate.find(queryString, objects);
        return list.isEmpty() ? null : (MeasureEntity) list.get(0);
    }

    private String findListByAccountNumberAndQueryStringAndObjects(String accountNumber, Boolean visited, String queryString, Object[] objects) throws Exception {
        if (visited) {
            Iterator iterator = hibernateTemplate.iterate(queryString, objects);
            return JSONUtils.toJSON(iterator);
        }
        List list = hibernateTemplate.find(queryString, objects);
        return JSONUtils.toJSON(list);
    }

    public void update(MeasureEntity measureEntity) {
        hibernateTemplate.update(measureEntity);
    }

    public void delete(MeasureEntity measureEntity) {
        hibernateTemplate.delete(measureEntity);
    }
}
