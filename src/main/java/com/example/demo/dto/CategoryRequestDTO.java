package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequestDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    private final String name;

    public CategoryRequestDTO(String name) {
        this.name = name;
    }

    public String getName() { return name; }
}
