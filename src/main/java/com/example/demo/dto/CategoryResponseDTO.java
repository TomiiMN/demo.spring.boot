package com.example.demo.dto;

import java.util.List;

public class CategoryResponseDTO {
    private final Long id;
    private final String name;
    private final List<String> productNames;

    public CategoryResponseDTO(Long id, String name, List<String> productNames) {
        this.id = id;
        this.name = name;
        this.productNames = productNames;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<String> getProductNames() { return productNames; }
}
