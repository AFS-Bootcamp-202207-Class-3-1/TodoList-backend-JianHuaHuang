package com.rest.springbootemployee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

    private String name;

    private Integer age;

    private String gender;

    private Integer salary;

}
