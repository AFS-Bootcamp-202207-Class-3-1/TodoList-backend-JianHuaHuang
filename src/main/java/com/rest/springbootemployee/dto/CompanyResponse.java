package com.rest.springbootemployee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author KiraYous
 * @version V1.0
 * @Package com.rest.springbootemployee.dto
 * @date 2022/7/30 18:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {
    private Integer id;

    private String companyName;

    private List<EmployeeResponse> employeeResponses;
}
