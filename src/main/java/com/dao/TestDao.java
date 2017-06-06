package com.dao;

import com.entity.MeasureEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 63289 on 2017/5/11.
 */
@Repository
public class TestDao {
    private final HibernateTemplate hibernateTemplate;

    @Autowired
    public TestDao(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @Transactional
    public void addByList(List<MeasureEntity> measureEntities){
        for(MeasureEntity measureEntity:measureEntities){
            hibernateTemplate.save(measureEntity);
        }
    }
}
