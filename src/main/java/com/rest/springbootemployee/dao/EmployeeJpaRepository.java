package com.rest.springbootemployee.dao;

import com.rest.springbootemployee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeJpaRepository extends JpaRepository<Employee,Integer> {


    List<Employee> findEmployeesByGender(String gender);
}
