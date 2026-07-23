package com.example.demo.service;

import com.example.demo.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private List<Product> products = new ArrayList<>(List.of(
       new Product(1, "Mouse", 1500),
       new Product(2, "Teclado", 2500),
       new Product(3, "Monitor", 45000)
    ));

    public List<Product> findAll() {
        return products;
    }
    public Product findById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }
    public Product create(Product product) {
        products.add(product);
        return product;
    }
    public void delete(int id) {
        Product product = findById(id);
        products.remove(product);
    }
}
