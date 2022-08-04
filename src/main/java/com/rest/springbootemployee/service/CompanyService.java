package com.rest.springbootemployee.service;


import com.rest.springbootemployee.dao.CompanyJpaRepository;
import com.rest.springbootemployee.dao.impl.CompanyRepository;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exceptions.CompanyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CompanyJpaRepository companyJpaRepository;

    public List<Company> findAll(){
        return companyJpaRepository.findAll();
    }

    public Company findById( int id ){
      return companyJpaRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
    }

    public List<Employee> findAllEmployeesById(int id) {
        return findById(id).getEmployees();
    }

    public Page<Company> findByPage(int page, int pageSize) {
        return companyJpaRepository.findAll(PageRequest.of(page,pageSize));
    }

    public Company insertCompany(Company company) {
        return companyJpaRepository.save(company);
    }

    public Company updateCompany(Integer id,Company company) {
        Company preCompany=findById(id);
        if (company.getCompanyName()!=null){
            preCompany.setCompanyName(company.getCompanyName());
        }
        return companyJpaRepository.save(preCompany);
    }

    public void deleteCompany(Integer id) {
        if(!companyJpaRepository.existsById(id)) throw new CompanyNotFoundException();
        companyJpaRepository.deleteById(id);
    }
}
