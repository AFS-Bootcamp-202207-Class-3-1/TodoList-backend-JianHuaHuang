package com.kirayous.todos.dto.mapper;

import com.kirayous.todos.dto.TodoRequest;
import com.kirayous.todos.entity.Todo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    public Todo toEntity(TodoRequest todoRequest){
        Todo todo=new Todo();
        BeanUtils.copyProperties(todoRequest,todo);
        return todo;
    }


}
