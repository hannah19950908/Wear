package com.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by 63289 on 2017/4/27.
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Your token is invalid.")
public class TokenException extends Exception{
    public TokenException(){
        super("Your token is invalid.");
    }
}
