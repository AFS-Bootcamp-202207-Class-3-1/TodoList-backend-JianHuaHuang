package com.kirayous.todos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirayous.todos.SpringBootTodoListApplication;
import com.kirayous.todos.dao.TodoJpaRepository;
import com.kirayous.todos.entity.Todo;
import com.kirayous.todos.exceptions.TodoNotFoundException;
import com.kirayous.todos.service.TodoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes= SpringBootTodoListApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TodoControllerTest {


    @Autowired
    @Resource
    MockMvc client;

    @Autowired
    TodoJpaRepository todoJpaRepository;

    @Autowired
    @Resource
    ObjectMapper objectMapper;
    @BeforeEach
    public void initDataBase() {
        todoJpaRepository.deleteAll();
        List<Todo> prepareTodoList = new ArrayList<>();
        System.out.println("-------initDatabase----------");
        for (int i = 1; i <= 10; i++) {
            Todo todo = new Todo();
            todo.setText("i want to get number " + i);
            todo.setDone(false);
            prepareTodoList.add(todo);
        }
        todoJpaRepository.saveAll(prepareTodoList);
        List<Todo> todos=todoJpaRepository.findAll();
        System.out.println(todos);
    }


    @Test
    void should_return_all_when_getAll_given_nothing() throws Exception {
        //given
        //when
        client.perform(MockMvcRequestBuilders.get("/todos")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", everyItem(isA(Integer.class))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].text", everyItem(matchesPattern("i want to get number \\d+"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].done", everyItem(is(false))));

        //then

    }

    @Test
    void should_return_new_Todo_when_insertTodo_given_new_Todo() throws Exception {
        String json=objectMapper.writeValueAsString(new Todo(null,"i want to sleep",true));
        client.perform(MockMvcRequestBuilders.post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("i want to sleep"));

    }

    @Test
    void should_return_updateTodo_when_updateTodoText_given_id_and_todo() throws Exception {

        List<Todo> todoList=todoJpaRepository.findAll();
        Todo preTodo=todoList.get(0);
        int id=preTodo.getId();
        String json=objectMapper.writeValueAsString(new Todo(null,"i want to sleep",preTodo.getDone()));

        //when
        client.perform(MockMvcRequestBuilders.put("/todos/{id}",preTodo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        //then
        //貌似不会级联更新
        Todo veriftTodo=todoJpaRepository.findById(id).orElseThrow(TodoNotFoundException::new);
        Assertions.assertEquals("i want to sleep",veriftTodo.getText());
    }
    @Test
    void should_return_updateTodo_when_updateTodoDone_given_id_and_todo() throws Exception {
        List<Todo> todoList=todoJpaRepository.findAll();
        Todo preTodo=todoList.get(0);
        int id=preTodo.getId();
        String json=objectMapper.writeValueAsString(new Todo(null,preTodo.getText(),true));

        //when
        client.perform(MockMvcRequestBuilders.put("/todos/{id}",preTodo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        //then
        //貌似不会级联更新
        Todo veriftTodo=todoJpaRepository.findById(id).orElseThrow(TodoNotFoundException::new);
        Assertions.assertEquals(true,veriftTodo.getDone());
    }

    @Test
    void deleteTodo() throws Exception {
        List<Todo>todoList=todoJpaRepository.findAll();
        Todo preTodo=todoList.get(0);
        //when
        client.perform(MockMvcRequestBuilders.delete("/todos/{id}",preTodo.getId())
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
        List<Todo> todos=todoJpaRepository.findAll();
        //then
        Assertions.assertNotNull(todoList);
        Assertions.assertEquals(todoList.size()-1, todos.size());
    }

    @Test
    public void should_return_Todo_not_found_when_deleteTodo_given_not_exists_id() throws Exception {
        //given
        List<Todo> preCompanyList=todoJpaRepository.findAll();
        Todo company=preCompanyList.stream().max(Comparator.comparingInt(Todo::getId)).orElseThrow(TodoNotFoundException::new);
        //when
        client.perform(MockMvcRequestBuilders.delete("/todos/{id}", company.getId()+1))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("TodoNotFound"));
        //then
    }
}