package com.dao;

import com.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 63289 on 2017/2/24.
 */
@Repository
public class UserDao {
    private final HibernateTemplate hibernateTemplate;

    @Autowired
    public UserDao(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @Transactional()
    public void add(UserEntity userEntity) {
        hibernateTemplate.save(userEntity);
    }

    public List findByExample(UserEntity userEntity) {
        return hibernateTemplate.findByExample(userEntity);
    }

    @Transactional
    public void update(UserEntity userEntity) {
        hibernateTemplate.update(userEntity);
    }

    @Transactional
    public void delete(UserEntity userEntity) {
        hibernateTemplate.delete(userEntity);
    }
}
