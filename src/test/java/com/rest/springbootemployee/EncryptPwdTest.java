package com.rest.springbootemployee;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.MethodName.class)
public class EncryptPwdTest {
    @Autowired
    StringEncryptor encryptor;

//    @Test
//    public void getPass() {
//        String url = encryptor.encrypt("jdbc:mysql://127.0.0.1:3306/employee_database?characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2b8");
//        String name = encryptor.encrypt("root");
//        String password = encryptor.encrypt("root");
//        System.out.println(url);
//        System.out.println(name);
//        System.out.println(password);
//    }
    @Test
    @Order(1)
    public void test1(){
        System.out.println("_______test1");
    }
    @Test
    @Order(4)
    public void test2(){
        System.out.println("_______test2");
    }
    @Test
    @Order(3)
    public void test3(){
        System.out.println("_______test3");
    }    @Test
    @Order(5)
    public void test4(){
        System.out.println("_______test4");
    }
    @Test
    @Order(2)
    public void test5(){
        System.out.println("_______test5");
        System.out.println("解码:"+encryptor.decrypt("k3cG4gv3SIMtTRa3NQJcwwdRZLoc0P9b"));
    }
}
