package com.rest.springbootemployee.companyTest.controllerTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.springbootemployee.SpringBootEmployeeApplication;
import com.rest.springbootemployee.dao.CompanyJpaRepository;
import com.rest.springbootemployee.dao.EmployeeJpaRepository;
import com.rest.springbootemployee.dao.impl.CompanyRepository;
import com.rest.springbootemployee.dto.CompanyResponse;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exceptions.CompanyNotFoundException;
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

import static org.hamcrest.Matchers.*;

@SpringBootTest(classes= SpringBootEmployeeApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SpringBootCompanyApplicationTests {
    @Autowired
    @Resource
    MockMvc client;
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CompanyJpaRepository companyJpaRepository;

    @Autowired
    EmployeeJpaRepository employeeJpaRepository;

    @Autowired
    @Resource
    ObjectMapper objectMapper;

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

    @Test
    public void should_get_all_company_when_get_findAll_given_nothing() throws Exception {
        //given
        //when
        client.perform(MockMvcRequestBuilders.get("/company")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", everyItem(isA(Integer.class))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].companyName", everyItem(matchesPattern("company\\d+"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].employeeResponses", hasSize(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].employeeResponses[*]", hasSize(50)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].employeeResponses[*].name", everyItem(matchesPattern("employee\\d+"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].employeeResponses[*].age", everyItem(is(20))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].employeeResponses[*].gender", everyItem(matchesPattern("[男女]"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].employeeResponses[*].salary", everyItem(is(10000))));

        //then


    }
    @Test
    void should_get_special_company_when_get_findById_given_Id() throws Exception {
        //given
        List<Company>companyList=companyJpaRepository.findAll();
        Company preCompany=companyList.get(0);
        //when
        client.perform(MockMvcRequestBuilders.get("/company/{id}",preCompany.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value(preCompany.getCompanyName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeResponses", hasSize(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeResponses[*].name", everyItem(matchesPattern("employee\\d+"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeResponses[*].age", everyItem(is(20))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeResponses[*].gender", everyItem(matchesPattern("[男女]"))));

        //then

    }
    @Test
    void should_get_special_company_when_findCompanyById_given_id() throws Exception {
        //given
        List<Company>companyList=companyJpaRepository.findAll();
        Company preCompany=companyList.get(0);
        //when
        client.perform(MockMvcRequestBuilders.get("/company/{id}/employees",preCompany.getId())
                ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", everyItem(matchesPattern("employee\\d+"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].age", everyItem(is(20))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", everyItem(matchesPattern("[男女]"))));
        //then

    }
    @Test
    void should_get_special_page_CompanyList_when_get_findByPage_given_page_and_pageSize() throws Exception {
        //given
        //when
        client.perform(MockMvcRequestBuilders.get("/company")
                        .param("page", "1")
                        .param("pageSize","5")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].companyName", everyItem(matchesPattern("company\\d+"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].employeeResponses[*].name", everyItem(matchesPattern("employee\\d+"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].employeeResponses[*].age", everyItem(is(20))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].employeeResponses[*].gender", everyItem(matchesPattern("[男女]"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].employeeResponses[*].salary", everyItem(is(10000))));
        //then
    }

    @Test
    void should_get_Company_when_put_updateCompany_given_and_company_and_id() throws Exception {
        //given
        List<Company>companyList=companyJpaRepository.findAll();
        Company preCompany=companyList.get(0);
        List<Employee>preEmployeeList =preCompany.getEmployees();
        preEmployeeList.get(0).setName("我是啊啊啊啊啊啊啊");
        String json=objectMapper.writeValueAsString(new Company(null,"company55555",preEmployeeList));

        //when
        client.perform(MockMvcRequestBuilders.put("/company/{id}",preCompany.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        //then
        //貌似不会级联更新
        Company veriftCompany=companyJpaRepository.findById(preCompany.getId()).orElseThrow(CompanyNotFoundException::new);
        Assertions.assertEquals("company55555",veriftCompany.getCompanyName());

    }

    @Test
    void should_get_new_company_when_post_insertCompany_given_a_new_company() throws Exception {
        //given
        String json=objectMapper.writeValueAsString(new Company(null,"company55555",null));
        //when
        client.perform(MockMvcRequestBuilders.post("/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("company55555"));
        //then

    }

    @Test
    void should_get_special_company_when_delete_deleteCompany_given_id() throws Exception {
        //given
        List<Company>companyList=companyJpaRepository.findAll();
        Company preCompany=companyList.get(0);

        //when
        client.perform(MockMvcRequestBuilders.delete("/company/{id}",preCompany.getId())
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
        List<Company> companies=companyJpaRepository.findAll();
        //then
        Assertions.assertNotNull(companyList);
        Assertions.assertEquals(companyList.size()-1, companies.size());
    }
    @Test
    public void should_return_company_not_found_when_findById_given_not_exists_id() throws Exception {
        //given
        List<Company> preCompanyList=companyJpaRepository.findAll();
        Company company=preCompanyList.stream().max(Comparator.comparingInt(Company::getId)).orElseThrow(EmployeeNotFoundException::new);
        //when
        client.perform(MockMvcRequestBuilders.get("/company/{id}", company.getId()+1))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("CompanyNotFoundException"));
        //then
    }
}
