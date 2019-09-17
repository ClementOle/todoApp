package com.colewski.todo.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Task {

    private Integer id;
    private String text;
}
