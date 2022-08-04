package com.rest.springbootemployee.companyTest.serviceTests;

import com.rest.springbootemployee.dao.CompanyJpaRepository;
import com.rest.springbootemployee.dao.impl.CompanyRepository;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
public class companyServiceTests {

    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyService companyService;

    @Mock
    CompanyJpaRepository companyJpaRepository;

    @Test
    public void should_return_all_company_when_findAll_given_nothing() {
        //given
        List<Company> preparedCompanys=new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Employee employee = new Employee(i, "Sally" + i, 22, "男", 10000,i);
            preparedCompanys.add(new Company(i,"company"+i,employee));
        }

        //when
        Mockito.when(companyJpaRepository.findAll()).thenReturn(preparedCompanys);
        List<Company> companyList = companyService.findAll();
        //then
        Assertions.assertEquals(2,companyList.size());
        Assertions.assertEquals(preparedCompanys, companyList);
    }

    @Test
    public void should_return_special_company_when_findById_given_id() {
        //given
        List<Company> preparedCompanys=new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Employee employee = new Employee(i, "Sally" + i, 22, "男", 10000,i);
            preparedCompanys.add(new Company(i,"company"+i,employee));
        }

        //when
        Mockito.when(companyJpaRepository.findById(1)).thenReturn(Optional.ofNullable(preparedCompanys.get(0)));
        Company company = companyService.findById(1);
        //then
        Assertions.assertEquals(preparedCompanys.get(0).getId(), company.getId());
        Assertions.assertEquals(preparedCompanys.get(0).getCompanyName(), company.getCompanyName());
        Assertions.assertEquals(preparedCompanys.get(0).getEmployees().get(0).getAge(), company.getEmployees().get(0).getAge());
        Assertions.assertEquals(preparedCompanys.get(0).getEmployees().get(0).getGender(), company.getEmployees().get(0).getGender());
        Assertions.assertEquals(preparedCompanys.get(0).getEmployees().get(0).getName(), company.getEmployees().get(0).getName());
        Assertions.assertEquals(preparedCompanys.get(0).getEmployees().get(0).getSalary(), company.getEmployees().get(0).getSalary());
    }



    @Test
    public void should_return_special_employees_when_findById_given_id() {
        //given
        List<Company> preparedCompanys=new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Employee employee = new Employee(i, "Sally" + i, 22, "男", 10000,i);
            preparedCompanys.add(new Company(i,"company"+i,employee));
        }

        //when
        Mockito.when(companyJpaRepository.findById(1)).thenReturn(Optional.ofNullable(preparedCompanys.get(0)));
        List<Employee> employees = companyService.findAllEmployeesById(1);
        //then
        Assertions.assertEquals(1, employees.size());
        Assertions.assertEquals(employees, preparedCompanys.get(0).getEmployees());
    }

    @Test
    public void should_update_only_name_when_update_given_company() {
        //given

        Company prepareCompany = new Company(1, "company100" ,null);
        //when
        Mockito.when(companyJpaRepository.findById(1)).thenReturn(Optional.of(prepareCompany));
        Mockito.when(companyService.updateCompany(1,prepareCompany)).thenReturn(prepareCompany);
        Company company = companyService.updateCompany(1, prepareCompany);
        //then
        Assertions.assertEquals(prepareCompany.getId(), company.getId());
        Assertions.assertEquals(prepareCompany.getCompanyName(), company.getCompanyName());


    }

    @Test
    public void should_return_insert_company_when_insertCompany_given_company() {
        //given
        Company prepareCompany = new Company(null, "company100" ,null);
        Mockito.when(companyJpaRepository.save(prepareCompany)).thenReturn(prepareCompany);

        //when
        Company company = companyService.insertCompany(prepareCompany);
        //then
        Assertions.assertEquals(prepareCompany.getId(), company.getId());
        Assertions.assertEquals(prepareCompany.getCompanyName(), company.getCompanyName());

    }


    @Test
    public void should_return_delete_employee_when_deleteEmployee_given_id() {
        //given
        companyJpaRepository.deleteById(1);
        //then
        Mockito.verify(companyJpaRepository, Mockito.times(1)).deleteById(1);
        //then
    }
}
