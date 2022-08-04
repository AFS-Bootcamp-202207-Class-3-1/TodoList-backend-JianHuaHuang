package com.kirayous.todos.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TodoNotFoundException extends RuntimeException {


    private String code = "404";

    public TodoNotFoundException() {
        super("TodoNotFound");
    }
}
