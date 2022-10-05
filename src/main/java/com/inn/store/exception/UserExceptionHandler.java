package com.inn.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserExceptionHandler extends RuntimeException{
    private static final long serialVersionUID=1L;

    public UserExceptionHandler(String message){
        super(message);
    }



}
