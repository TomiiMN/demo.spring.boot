package com.example.demo.dto;

public class ProductResponseDTO {
    private final Long id;
    private final String name;
    private final double price;
    private final String categoryName;

    public ProductResponseDTO(Long id, String name, double price, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategoryName() { return categoryName; }
}
