package com.rest.springbootemployee.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmployeeNotFoundException extends RuntimeException{
    String code="404";
    public EmployeeNotFoundException() {
        super("EmployeeNotFound");
    }
}
