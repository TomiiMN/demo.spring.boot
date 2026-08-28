package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductRequestDTO {
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private final String name;

    @Positive(message = "El precio debe ser mayor a 0")
    private final double price;

    @NotNull(message = "Debe indicar una categoría")
    private final Long categoryId;

    public ProductRequestDTO(String name, double price, Long categoryId) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public Long getCategoryId() { return categoryId; }
}
