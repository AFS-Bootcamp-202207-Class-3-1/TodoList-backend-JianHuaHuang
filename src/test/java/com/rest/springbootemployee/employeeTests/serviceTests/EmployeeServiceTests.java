package com.rest.springbootemployee.employeeTests.serviceTests;

import com.rest.springbootemployee.dao.EmployeeJpaRepository;
import com.rest.springbootemployee.dao.impl.EmployeeRepository;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.service.EmployeeService;
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTests {

    @InjectMocks
    EmployeeService employeeService;

    @Mock
    EmployeeJpaRepository employeeJpaRepository;

    @Test
    public void should_return_all_employee_when_findAll_given_nothing() {
        //given
        List<Employee> preparedEmployees=new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Employee employee = new Employee(i, "Sally" + i, 22, "男", 10000,1);
            preparedEmployees.add(employee);
        }

        //when
        Mockito.when(employeeJpaRepository.findAll()).thenReturn(preparedEmployees);
        List<Employee> employeeList = employeeService.findAll();
        //then
        Assertions.assertEquals(2,employeeList.size());
        Assertions.assertEquals(preparedEmployees, employeeList);
    }

    @Test
    public void should_return_special_employee_when_findById_given_id() {
        //given
        List<Employee> preparedEmployees=new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Employee employee = new Employee(i, "Sally" + i, 22, "男", 10000,1);
            preparedEmployees.add(employee);
        }

        //when
        Mockito.when(employeeJpaRepository.findById(1)).thenReturn(Optional.ofNullable(preparedEmployees.get(0)));
        Employee employee = employeeService.findById(1);
        //then
        Assertions.assertEquals(preparedEmployees.get(0).getId(), employee.getId());
        Assertions.assertEquals(preparedEmployees.get(0).getAge(), employee.getAge());
        Assertions.assertEquals(preparedEmployees.get(0).getGender(), employee.getGender());
        Assertions.assertEquals(preparedEmployees.get(0).getName(), employee.getName());
        Assertions.assertEquals(preparedEmployees.get(0).getSalary(), employee.getSalary());
    }



    @Test
    public void should_return_special_employee_when_findByGender_given_gender() {
        //given
        List<Employee> preparedEmployees=new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Employee employee = new Employee(i, "Sally" + i, 22, (i&1)==0?"女":"男", 10000,1);
            preparedEmployees.add(employee);
        }

        //when
        Mockito.when(employeeJpaRepository.findEmployeesByGender("女"))
                .thenReturn(preparedEmployees.stream()
                        .filter(employee -> Objects.equals(employee.getGender(), "女"))
                        .collect(Collectors.toList()));
        List<Employee> employees = employeeService.findByGender("女");
        //then
        Assertions.assertEquals(2, employees.size());
        Assertions.assertEquals(employees.get(0), preparedEmployees.get(1));
    }

    @Test
    public void should_update_only_age_and_salary_when_update_given_employee() {
        //given

        Employee prepareEmployee = new Employee(2, "Sally" + 2, 20, "女", 12000,1);
        //when
        Mockito.when(employeeJpaRepository.save(prepareEmployee)).thenReturn(prepareEmployee);
        Mockito.when(employeeJpaRepository.findById(2)).thenReturn(Optional.of(prepareEmployee));
        Employee employee = employeeService.updateEmployee(2, prepareEmployee);
        //then
        Assertions.assertEquals(prepareEmployee.getId(), employee.getId());
        Assertions.assertEquals(prepareEmployee.getName(), employee.getName());
        Assertions.assertEquals(prepareEmployee.getAge(), employee.getAge());
        Assertions.assertEquals(prepareEmployee.getGender(), employee.getGender());
        Assertions.assertEquals(prepareEmployee.getSalary(), employee.getSalary());
    }

    @Test
    public void should_return_insert_employee_when_insertEmployee_given_employee() {
        //given
        Employee prepareEmployee = new Employee(2, "Sally" + 2, 20, "女", 12000,1);
        Mockito.when(employeeJpaRepository.save(prepareEmployee)).thenReturn(prepareEmployee);

        //when
        Employee employee = employeeService.insertEmployee(prepareEmployee);
        //then
        Assertions.assertEquals(prepareEmployee.getId(), employee.getId());
        Assertions.assertEquals(prepareEmployee.getName(), employee.getName());
        Assertions.assertEquals(prepareEmployee.getAge(), employee.getAge());
        Assertions.assertEquals(prepareEmployee.getGender(), employee.getGender());
        Assertions.assertEquals(prepareEmployee.getSalary(), employee.getSalary());

    }


    @Test
    public void should_return_delete_employee_when_deleteEmployee_given_id() {

        //given
        employeeJpaRepository.deleteById(1);
        //then
        Mockito.verify(employeeJpaRepository, Mockito.times(1)).deleteById(1);
        //then
    }


}
