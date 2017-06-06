package com.dao;

import com.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Properties;

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

    public void sendSimpleEmail(UserEntity userEntity)
    {
        String Email=userEntity.getEmail();
        JavaMailSenderImpl senderImpl  =   new  JavaMailSenderImpl();
        // 设定mail server
        senderImpl.setHost( "smtp.163.com" );

        // 建立邮件消息
        SimpleMailMessage mailMessage  =   new  SimpleMailMessage();
        // 设置收件人，寄件人 用数组发送多个邮件
        // String[] array = new String[]    {"sun111@163.com","sun222@sohu.com"};
        // mailMessage.setTo(array);
        mailMessage.setTo( Email );
        mailMessage.setFrom( "wearabletest@163.com" );
        mailMessage.setSubject( "测试简单文本邮件发送！" );
        mailMessage.setText( "测试我的简单邮件发送机制！！" );

        senderImpl.setUsername( "wearabletest@163.com" ) ;  //  根据自己的情况,设置username
        senderImpl.setPassword( "hahaschool" ) ;  //  根据自己的情况, 设置password

        Properties prop  =   new  Properties() ;
        prop.put( "mail.smtp.auth" ,  "true" ) ;  //  将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
        prop.put( "mail.smtp.timeout" ,  "25000" ) ;
        senderImpl.setJavaMailProperties(prop);
        // 发送邮件
        senderImpl.send(mailMessage);

    }
}
