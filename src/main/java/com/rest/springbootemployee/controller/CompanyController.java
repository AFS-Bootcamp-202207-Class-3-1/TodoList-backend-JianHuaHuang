package com.rest.springbootemployee.controller;


import com.rest.springbootemployee.dto.CompanyRequest;
import com.rest.springbootemployee.dto.CompanyResponse;
import com.rest.springbootemployee.dto.EmployeeResponse;
import com.rest.springbootemployee.dto.mapper.CompanyMapper;
import com.rest.springbootemployee.dto.mapper.EmployeeMapper;
import com.rest.springbootemployee.service.CompanyService;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyMapper companyMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @GetMapping
    public List<CompanyResponse> findAll(){
        return companyService
                .findAll()
                .stream()
                .map(company -> companyMapper.toCompanyResponse(company))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CompanyResponse findById(@PathVariable(value = "id") int id){
        return companyMapper.toCompanyResponse(companyService.findById(id));
    }
    @GetMapping("/{id}/employees")
    public List<EmployeeResponse> findAllEmployeesById(@PathVariable(value = "id") int id){
        return companyService
                .findAllEmployeesById(id)
                .stream()
                .map(employee -> employeeMapper.toEmployeeResponse(employee))
                .collect(Collectors.toList());
    }


    @GetMapping(params = {"page","pageSize"})
    public Page<CompanyResponse> findByPage(int page , int pageSize){
        return companyService
                .findByPage(page,pageSize)
                .map(company -> companyMapper.toCompanyResponse(company));
    }

    @PostMapping()
    public Company insertCompany(@RequestBody CompanyRequest companyRequest){
        return companyService.insertCompany(companyMapper.toEntity(companyRequest));
    }

    @PutMapping("/{id}")
    public  Company updateCompany(@PathVariable Integer id, @RequestBody CompanyRequest companyRequest){
        return companyService.updateCompany(id,companyMapper.toEntity(companyRequest));
    }
    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  void deleteCompany(@PathVariable Integer id){
        companyService.deleteCompany(id);
    }
}
