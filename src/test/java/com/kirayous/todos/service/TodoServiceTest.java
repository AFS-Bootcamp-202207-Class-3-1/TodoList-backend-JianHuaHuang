package com.kirayous.todos.service;

import com.kirayous.todos.dao.TodoJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class TodoServiceTest {

    @Mock
    TodoJpaRepository todoJpaRepository;

    @InjectMocks
    TodoService todoService;

    @Test
    void should_return_all_when_getAll_given_nothing() {

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