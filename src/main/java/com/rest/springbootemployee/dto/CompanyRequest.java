package com.rest.springbootemployee.dto;

import com.rest.springbootemployee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KiraYous
 * @version V1.0
 * @Package com.rest.springbootemployee.dto
 * @date 2022/7/30 18:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequest {
    private String companyName;
}
