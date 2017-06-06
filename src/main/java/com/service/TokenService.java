package com.service;

import com.Exception.TokenException;
import com.dao.TokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by 63289 on 2017/4/27.
 */
@Service
public class TokenService {
    private final TokenDao tokenDao;

    @Autowired
    public TokenService(TokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    public String generateToken(String accountNumber){
        String token= UUID.randomUUID().toString().replace("-","");
        tokenDao.put(token,accountNumber);
        tokenDao.put(accountNumber,token);
        return token;
    }

    public String getAccountNumber(String token) throws TokenException{
        String accountNumber=tokenDao.get(token);
        if (accountNumber == null) throw new TokenException();
        return accountNumber;
    }

    public String getToken(String accountNumber) throws TokenException{
        String token=tokenDao.get(accountNumber);
        return token;
    }

    public void delete(String token){
        tokenDao.delete(token);
    }
}
