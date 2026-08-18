package com.example.demo.dto;

public class ProductRequestDTO {
    private final String name;
    private final double price;
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
