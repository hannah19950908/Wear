package com.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by 63289 on 2017/4/27.
 */
@ResponseStatus(value = HttpStatus.CONFLICT,reason = "The account has exists")
public class UserSetupException extends Exception {
    public UserSetupException(){
        super("The account has exists");
    }
}
