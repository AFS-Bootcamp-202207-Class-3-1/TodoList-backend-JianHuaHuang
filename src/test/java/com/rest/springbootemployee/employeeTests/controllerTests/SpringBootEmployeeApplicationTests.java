package com.rest.springbootemployee.employeeTests.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.springbootemployee.SpringBootEmployeeApplication;
import com.rest.springbootemployee.dao.CompanyJpaRepository;
import com.rest.springbootemployee.dao.EmployeeJpaRepository;
import com.rest.springbootemployee.dao.impl.EmployeeRepository;
import com.rest.springbootemployee.dto.EmployeeRequest;
import com.rest.springbootemployee.dto.mapper.EmployeeMapper;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exceptions.EmployeeNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = SpringBootEmployeeApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class SpringBootEmployeeApplicationTests {
    @Autowired
    @Resource
    MockMvc client;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeJpaRepository employeeJpaRepository;

    @Autowired
    CompanyJpaRepository companyJpaRepository;

    @Autowired
    @Resource
    ObjectMapper objectMapper;

    @Autowired
    EmployeeMapper employeeMapper;
    @BeforeEach
    public void initDataBase() {
        employeeJpaRepository.deleteAll();
        companyJpaRepository.deleteAll();
        List<Company> prepareCompanyList = new ArrayList<>();
        List<Employee> prepareEmployeeList = new ArrayList<>();
        System.out.println("-------initCompanyDatabase----------");
        for (int i = 1; i <= 10; i++) {
            Company company = new Company();
            company.setCompanyName("company" + i);
            prepareCompanyList.add(company);
        }
        companyJpaRepository.saveAll(prepareCompanyList);
        List<Company> companies=companyJpaRepository.findAll();
        System.out.println(companies);

        System.out.println("-------initEmployeeDatabase----------");
        for (int i = 1, j = 1; i <= companies.size(); i++) {
            for (int k = 0; k < 5; k++, j++) {
                Employee employee = new Employee();
                employee.setName("employee" + j);
                employee.setAge(20);
                employee.setGender((j & 1) == 0 ? "女" : "男");
                employee.setSalary(10000);
                employee.setCompanyId(companies.get(i-1).getId());
                prepareEmployeeList.add(employee);
            }
        }
        employeeJpaRepository.saveAll(prepareEmployeeList);
        System.out.println(employeeJpaRepository.findAll());
    }

    @Order(1)
    @Test
    void should_get_all_employee_when_get_findAll_given_nothing() throws Exception {
        //given
        List<Employee> preEmployeeList=employeeJpaRepository.findAll();
        //when
        client.perform(MockMvcRequestBuilders.get("/employees")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(preEmployeeList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", everyItem(matchesPattern("employee\\d+"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].age", everyItem(is(20))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", everyItem(matchesPattern("[男女]"))));
        //then

    }

    @Order(2)
    @Test
    void should_get_special_employee_when_get_findById_given_Id() throws Exception {
        //given
        List<Employee> preEmployeeList=employeeJpaRepository.findAll();
        Employee employee=preEmployeeList.get(0);
        //when
        client.perform(MockMvcRequestBuilders.get("/employees/{id}", employee.getId())).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employee.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(employee.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(employee.getGender()));
        //then

    }

    @Order(3)
    @Test
    void should_get_special_gender_employee_when_get_findByGender_given_gender() throws Exception {
        //given
        //when
        client.perform(MockMvcRequestBuilders.get("/employees")
                        .param("gender", "女")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", everyItem(matchesPattern("employee\\d+"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].age", everyItem(is(20))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", everyItem(is("女"))));
        //then

    }

    @Order(4)



    @Test
    void should_get_special_page_employeeList_when_get_findByPage_given_page_and_pageSize() throws Exception {
        //given
        //when
        client.perform(MockMvcRequestBuilders.get("/employees")
                        .param("page", "1")
                        .param("pageSize", "5")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].name", everyItem(matchesPattern("employee\\d+"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].age", everyItem(is(20))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].gender", everyItem(matchesPattern("[男女]"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].salary", everyItem(is(10000))));
        //then
    }

    @Test
    public void should_return_employee_not_found_when_findById_given_not_exists_id() throws Exception {
        //given
        List<Employee> preEmployeeList=employeeJpaRepository.findAll();
        Employee employee=preEmployeeList.stream().max(Comparator.comparingInt(Employee::getId)).orElseThrow(EmployeeNotFoundException::new);
        //when
        client.perform(MockMvcRequestBuilders.get("/employees/{id}", employee.getId()+1))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("EmployeeNotFound"));
        //then
    }


    @Test
    void should_get_before_employee_when_put_updateEmployee_given_and_employ() throws Exception {
        //given
        List<Employee> preEmployeeList=employeeJpaRepository.findAll();
        Employee preEmployee=preEmployeeList.get(0);
        Integer id=preEmployee.getId();
        EmployeeRequest employeeRequest=new EmployeeRequest("Sally5",22,"男",10000);

        String updateEmployeeJson = objectMapper.writeValueAsString(employeeRequest);
        //when
        client.perform(MockMvcRequestBuilders.put("/employees/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateEmployeeJson)
                ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employeeRequest.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(employeeRequest.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(employeeRequest.getGender()));//then
        Optional<Employee> employeeOptional = employeeJpaRepository.findById(id);
        Assertions.assertNotNull(employeeOptional.orElse(null));
        Assertions.assertEquals(employeeRequest.getName(), employeeOptional.orElseGet(null).getName());
        Assertions.assertEquals(employeeRequest.getGender(), employeeOptional.orElseGet(null).getGender());
        Assertions.assertEquals(employeeRequest.getSalary(), employeeOptional.orElseGet(null).getSalary());
        Assertions.assertEquals(employeeRequest.getAge(), employeeOptional.orElseGet(null).getAge());

    }
    @Test
    void should_get_new_employee_when_post_insertEmployee_given_a_new_Employee() throws Exception {
        //given
        List<Company> companies=companyJpaRepository.findAll();
        Company company=companies.get(0);
        EmployeeRequest preEmployeeRequest=new EmployeeRequest("K",20,"男",10000);
        String json=objectMapper.writeValueAsString(preEmployeeRequest);
        //when
        client.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(preEmployeeRequest.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(preEmployeeRequest.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(preEmployeeRequest.getGender()));
        //then
    }


    @Test
    void should_get_special_employee_when_delete_deleteEmployee_given_id() throws Exception {
        //given
        List<Employee> preEmployeeList=employeeJpaRepository.findAll();
        Integer id=preEmployeeList.get(0).getId();
        //when
        client.perform(MockMvcRequestBuilders.delete("/employees/{id}", id)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
        List<Employee> employeeList = employeeJpaRepository.findAll();
        //then
        Assertions.assertEquals(preEmployeeList.size()-1, employeeList.size());
    }

}
