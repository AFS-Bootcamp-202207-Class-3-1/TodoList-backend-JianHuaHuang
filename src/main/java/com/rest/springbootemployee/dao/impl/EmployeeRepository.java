package com.rest.springbootemployee.dao.impl;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exceptions.EmployeeNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class EmployeeRepository {

    public List<Employee> employeeList;


    {
        employeeList = new ArrayList<Employee>() {{
            add(new Employee(1, "A", 10, "男", 100,1));
            add(new Employee(2, "B", 11, "女", 100,1));
            add(new Employee(3, "C", 12, "男", 100,1));
            add(new Employee(4, "D", 13, "女", 100,1));
            add(new Employee(5, "E", 14, "男", 100,1));
            add(new Employee(6, "F", 10, "女", 100,1));
            add(new Employee(7, "G", 11, "男", 100,1));
            add(new Employee(8, "H", 12, "女", 100,1));
            add(new Employee(9, "I", 13, "男", 100,1));
            add(new Employee(10, "J", 14, "女", 100,1));
        }};
    }


    public List<Employee> getAll() {
        return this.employeeList;
    }

    public Employee updateEmployeeList(Integer id,Employee employee) {
        List<Employee> employeeList =getAll(); // 为了迎合单元测试
        for (int i = 0; i < employeeList.size(); i++) {
            if (Objects.equals(employeeList.get(i).getId(), id)){
                employeeList.set(i,employee);
                break;
            }
        }
        return employee;
    }

    public void clearAll() {

        this.employeeList = new ArrayList<>();
    }

    public Employee insertEmployee(Employee employee) {
        List<Employee> employeeList=getAll(); // 为了迎合单元测试
        employeeList.add(employee);
        return employee;
    }

    public Employee findById(Integer id) {
        List<Employee> employeeList=getAll(); // 为了迎合单元测试
        return employeeList.stream()
                .filter(employee -> Objects.equals(employee.getId(), id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);

    }

    public Employee deleteEmployee(Integer id){
        List<Employee> employeeList=getAll();
        Employee employee=null;
        for (int i = 0; i < employeeList.size(); i++) {
            if (Objects.equals(employeeList.get(i).getId(), id)){
                employee=employeeList.get(i);
                employeeList.remove(i);
                break;
            }
        }
        return employee;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        System.out.println("_________________is here?");
        this.employeeList = employeeList;
    }
}
