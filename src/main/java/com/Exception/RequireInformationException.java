package com.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by 63289 on 2017/5/2.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Your require data is null or wrong.")
public class RequireInformationException extends Exception{
    public RequireInformationException(){
        super("Your require data is null or wrong.");
    }
}
