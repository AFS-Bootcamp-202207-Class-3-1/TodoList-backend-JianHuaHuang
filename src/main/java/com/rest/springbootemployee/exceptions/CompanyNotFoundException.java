package com.rest.springbootemployee.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CompanyNotFoundException extends RuntimeException{
    private String code ="404";
    public CompanyNotFoundException() {
        super("CompanyNotFoundException");
    }
}
