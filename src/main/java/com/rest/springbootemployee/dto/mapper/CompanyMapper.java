package com.rest.springbootemployee.dto.mapper;

import com.rest.springbootemployee.dto.CompanyRequest;
import com.rest.springbootemployee.dto.CompanyResponse;
import com.rest.springbootemployee.dto.EmployeeRequest;
import com.rest.springbootemployee.dto.EmployeeResponse;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author KiraYous
 * @version V1.0
 * @Package com.rest.springbootemployee.dto.mapper
 * @date 2022/7/30 18:15
 */
@Component
public class CompanyMapper {

    @Autowired
    EmployeeMapper employeeMapper;
    public CompanyResponse toCompanyResponse(Company company){
        CompanyResponse companyResponse=new CompanyResponse();
        BeanUtils.copyProperties(company,companyResponse);
        company.getEmployees().forEach(employee -> {
            if (companyResponse.getEmployeeResponses()==null)companyResponse.setEmployeeResponses(new ArrayList<>());
            companyResponse.getEmployeeResponses().add(employeeMapper.toEmployeeResponse(employee));
        });
        return companyResponse;
    }
    public Company toEntity(CompanyRequest companyRequest){
        Company company=new Company();
        BeanUtils.copyProperties(companyRequest,company);
        return company;
    }
}
