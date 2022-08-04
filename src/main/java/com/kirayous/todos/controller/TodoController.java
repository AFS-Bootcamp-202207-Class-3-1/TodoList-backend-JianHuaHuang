package com.kirayous.todos.controller;


import com.kirayous.todos.dto.TodoRequest;
import com.kirayous.todos.dto.mapper.TodoMapper;
import com.kirayous.todos.entity.Todo;
import com.kirayous.todos.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    TodoService todoService;

    @Autowired
    TodoMapper todoMapper;

    @GetMapping
    public List<Todo> getAll(){
        return todoService.getAll();
    }

    @PostMapping
    public Todo insertTodo(@RequestBody TodoRequest todoRequest){
        return todoService.insertTodo(todoMapper.toEntity(todoRequest));
    }

    @PutMapping("/{id}")
    public Todo updateTodoText(@PathVariable Integer id,@RequestBody TodoRequest todoRequest){
        return todoService.updateTodoText(id,todoMapper.toEntity(todoRequest));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteTodo(@PathVariable Integer id){
        todoService.deleteTodo(id);
    }

}
