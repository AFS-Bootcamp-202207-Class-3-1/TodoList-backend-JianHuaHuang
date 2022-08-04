package com.rest.springbootemployee.dao.impl;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {

    List<Company> companyList;

    {
        companyList=new ArrayList<>();

        for (int i = 1, j=1 ; i <=10 ; i++) {
            Company company=new Company(i,"company"+i,new ArrayList<>());
            for(int k=0;k<5;k++,j++){
                company.getEmployees().add(new Employee(j, "employee"+j,(int)(Math.random()*35) + 20, (j&1)==0 ? "女": "男", (int)(Math.random()*15000) + 10000,i));
            }
            companyList.add(company);
        }

    }

    public List<Company> getAll() {
        return companyList;
    }

    public void updateCompanyList(List<Company> companyList) {
        this.companyList=companyList;
    }

    public void clearAll() {
        this.companyList=new ArrayList<>();
    }

    public void insertCompany(Company company) {
        List<Company> companyList=getAll();
        if(companyList!=null)
            companyList.add(company);
    }
}
