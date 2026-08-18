package com.example.demo.dto;

public class CategoryRequestDTO {
    private final String name;

    public CategoryRequestDTO(String name) {
        this.name = name;
    }

    public String getName() { return name; }
}
