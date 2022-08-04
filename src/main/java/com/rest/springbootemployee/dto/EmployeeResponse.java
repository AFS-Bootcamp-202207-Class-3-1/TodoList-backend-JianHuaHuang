package com.rest.springbootemployee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

    private Integer id;

    private String name;

    private Integer age;

    private String gender;
}
