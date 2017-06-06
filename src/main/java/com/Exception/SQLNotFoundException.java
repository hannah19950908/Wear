package com.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by 63289 on 2017/4/27.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "No information found.")
public class SQLNotFoundException extends Exception {
    public SQLNotFoundException(){
        super("No information found.");
    }
}
