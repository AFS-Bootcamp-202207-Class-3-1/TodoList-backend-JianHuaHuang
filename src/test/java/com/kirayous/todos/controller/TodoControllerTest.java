package com.kirayous.todos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirayous.todos.SpringBootTodoListApplication;
import com.kirayous.todos.dao.TodoJpaRepository;
import com.kirayous.todos.entity.Todo;
import com.kirayous.todos.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes= SpringBootTodoListApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TodoControllerTest {


    @Autowired
    MockMvc client;

    @Autowired
    TodoJpaRepository todoJpaRepository;

    @Autowired
    ObjectMapper objectMapper;
    @BeforeEach
    public void initDataBase() {
        todoJpaRepository.deleteAll();
        List<Todo> prepareTodoList = new ArrayList<>();
        System.out.println("-------initDatabase----------");
        for (int i = 1; i <= 10; i++) {
            Todo todo = new Todo();
            todo.setText("i want to get number " + i);
            prepareTodoList.add(todo);
        }
        todoJpaRepository.saveAll(prepareTodoList);
        List<Todo> todos=todoJpaRepository.findAll();
        System.out.println(todos);
    }


    @Test
    void should_return_all_when_getAll_given_nothing() {
        //given
        //when
        client.perform(MockMvcRequestBuilders.get("/todos")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", everyItem(isA(Integer.class))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].text", everyItem(matchesPattern("i want to get number \\d+"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].done", everyItem(is(20))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].employeeResponses", everyItem(matchesPattern("[男女]"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].employeeResponses", everyItem(is(10000))));

        //then

    }

    @Test
    void insertTodo() {
    }

    @Test
    void updateTodoText() {
    }

    @Test
    void deleteTodo() {
    }
}