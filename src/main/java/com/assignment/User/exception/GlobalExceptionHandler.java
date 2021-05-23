package com.assignment.User.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundExceptionHandler(UserNotFoundException ex, HttpServletRequest req, WebRequest webRequest)
    {
        Map<String, Object> errorBody = new LinkedHashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("message", ex.getMessage());
        errorBody.put("status", HttpStatus.NOT_FOUND);
        errorBody.put("path", webRequest.getDescription(false));
        logger.debug("userNotFoundExceptionHandler::"+errorBody);
        return new  ResponseEntity<>(errorBody,HttpStatus.NOT_FOUND);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotValidException.class)
    public ResponseEntity<?> userNotValidExceptionHandler(UserNotValidException ex,WebRequest webRequest)
    {
        Map<String, Object> errorBody = new LinkedHashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());

        BindingResult bindingResult = ex.getBindingResult();

        errorBody.put("message",  bindingResult.getAllErrors().get(0).getObjectName()
                +"->"+((FieldError)bindingResult.getAllErrors().get(0)).getField()
                +"->"+bindingResult.getAllErrors().get(0).getDefaultMessage());

        ObjectError objectError = bindingResult.getAllErrors().get(0);
        errorBody.put("status", HttpStatus.BAD_REQUEST);
        errorBody.put("path", webRequest.getDescription(false));
        logger.debug("userNotValidExceptionHandler::"+errorBody);
        return new  ResponseEntity<>(errorBody,HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoUserInDatabaseException.class)
    public ResponseEntity<?> noUserInDatabaseExceptionHandler(WebRequest webRequest)
    {
        Map<String, Object> errorBody = new LinkedHashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("message","No User Available in Database, Please Add new User ");
        errorBody.put("status", HttpStatus.NOT_FOUND);
        errorBody.put("path", webRequest.getDescription(false));
        logger.debug("noUserInDatabaseExceptionHandler::"+errorBody);
        return new  ResponseEntity<>(errorBody,HttpStatus.NOT_FOUND);
    }
}

