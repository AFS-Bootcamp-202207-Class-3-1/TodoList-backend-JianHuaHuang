package com.rest.springbootemployee.service;

import com.rest.springbootemployee.dao.EmployeeJpaRepository;
import com.rest.springbootemployee.dao.impl.EmployeeRepository;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exceptions.EmployeeNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeJpaRepository employeeJpaRepository;

    public List<Employee> findAll() {
        return employeeJpaRepository.findAll();
    }

    public Employee findById(Integer id) {
        return employeeJpaRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employeeJpaRepository.findEmployeesByGender(gender);
    }


    public Page<Employee> findByPage(int page, int pageSize) {
        return employeeJpaRepository.findAll(PageRequest.of(page,pageSize));
    }

    public Employee insertEmployee(Employee employee) {
        return employeeJpaRepository.save(employee);
    }

    public Employee updateEmployee(Integer id, Employee employee) {

        Employee em=findById(id);

        if(StringUtils.isNoneBlank(employee.getName())){
            em.setName(employee.getName());
        }
        if (StringUtils.isNoneBlank(employee.getGender())){
            em.setGender(employee.getGender());
        }
        if(Objects.nonNull(employee.getAge())){
            em.setAge(employee.getAge());
        }
        if (Objects.nonNull(employee.getSalary())){
            em.setSalary(employee.getSalary());
        }
        return employeeJpaRepository.save(em);
    }

    public void deleteEmployee(Integer id) {

        if(!employeeJpaRepository.existsById(id)) throw new EmployeeNotFoundException();
        employeeJpaRepository.deleteById(id);
    }
}
