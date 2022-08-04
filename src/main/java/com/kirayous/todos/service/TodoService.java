package com.kirayous.todos.service;

import com.kirayous.todos.dao.TodoJpaRepository;
import com.kirayous.todos.entity.Todo;
import com.kirayous.todos.exceptions.TodoNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TodoService {

    @Autowired
    TodoJpaRepository todoJpaRepository;
    public List<Todo> getAll(){
        return todoJpaRepository.findAll();
    }

    public Todo insertTodo(Todo todo) {
        return todoJpaRepository.save(todo);
    }

    public Todo updateTodoText(Integer id, Todo toEntity) {
        if(Objects.isNull(id))throw new TodoNotFoundException();
        Todo todo=todoJpaRepository.findById(id).orElseThrow(TodoNotFoundException::new);

        if (StringUtils.isNoneBlank(toEntity.getText())){
            todo.setText(toEntity.getText());
        }

        if(Objects.nonNull(toEntity.getDone())){
            todo.setDone(toEntity.getDone());
        }
        return todoJpaRepository.save(todo);
    }

    public void deleteTodo(Integer id) {
        if (Objects.isNull(id))throw new TodoNotFoundException();
        Todo todo=todoJpaRepository.findById(id).orElseThrow(TodoNotFoundException::new);
        todoJpaRepository.delete(todo);
    }
}
