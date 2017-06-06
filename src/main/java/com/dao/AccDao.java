package com.dao;

import com.entity.AccEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 63289 on 2017/4/17.
 */
@Repository
public class AccDao {
    private final HibernateTemplate hibernateTemplate;

    @Autowired
    public AccDao(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @Transactional
    public void add(AccEntity accEntity) {
        hibernateTemplate.save(accEntity);
    }

    public List<AccEntity> findByExample(AccEntity accEntity){
        return hibernateTemplate.findByExample(accEntity);
    }

    @Transactional
    public void update(AccEntity accEntity){
        hibernateTemplate.update(accEntity);
    }

    @Transactional
    public void delete(AccEntity accEntity){
        hibernateTemplate.delete(accEntity);
    }
}
