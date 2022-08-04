package com.rest.springbootemployee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = EmployeeNotFoundException.class)
    public Map<String, String> myErrorHandler(EmployeeNotFoundException ex) {
        Map<String, String> resMap = new HashMap<>();
        resMap.put("code", ex.getCode());
        resMap.put("msg", ex.getMessage());
        return resMap;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value =CompanyNotFoundException.class)
    public Map<String, String> myErrorHandler(CompanyNotFoundException ex) {
        Map<String, String> resMap = new HashMap<>();
        resMap.put("code", ex.getCode());
        resMap.put("msg", ex.getMessage());
        return resMap;
    }


}
