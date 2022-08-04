package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.dto.EmployeeRequest;
import com.rest.springbootemployee.dto.EmployeeResponse;
import com.rest.springbootemployee.dto.mapper.EmployeeMapper;
import com.rest.springbootemployee.service.EmployeeService;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {


    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeMapper employeeMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse findById(@PathVariable(value = "id") int id) {

        return employeeMapper.toEmployeeResponse(employeeService.findById(id));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)

    public List<EmployeeResponse> findAll() {
        return employeeService.findAll()
                .stream()
                .map(employee -> employeeMapper.toEmployeeResponse(employee))
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"gender"})
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeResponse> findByGender(@RequestParam(value = "gender") String gender) {

        return employeeService.findByGender(gender)
                .stream()
                .map(employee -> employeeMapper.toEmployeeResponse(employee))
                .collect(Collectors.toList());
    }

    //TODO 类型
    @GetMapping(params = {"page", "pageSize"})
    @ResponseStatus(HttpStatus.OK)

    public Page<EmployeeResponse> findByPage(int page, int pageSize) {
        Page<Employee> employeePage =employeeService.findByPage(page, pageSize);
        return employeePage.map(employee -> employeeMapper.toEmployeeResponse(employee));

    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)

    public EmployeeResponse insertEmployee(@RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeService.insertEmployee(employeeMapper.toEntity(employeeRequest));
        return employeeMapper.toEmployeeResponse(employee);
    }

    @PutMapping("/{id}")
    public EmployeeResponse updateEmployee(@PathVariable Integer id, @RequestBody EmployeeRequest employeeRequest) {
        Employee employee=employeeService.updateEmployee(id, employeeMapper.toEntity(employeeRequest));
        return employeeMapper.toEmployeeResponse(employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
    }

}
