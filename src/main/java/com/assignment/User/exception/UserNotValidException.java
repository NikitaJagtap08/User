package com.assignment.User.exception;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

public class UserNotValidException extends BindException{
    public UserNotValidException(BindingResult bindingResult)
    {
        super(bindingResult);
    }
}
