package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAll() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(new ApiResponse<>("Products retrieved successfully", products));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>("Product retrieved successfully", product));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<Product>> create(@RequestBody Product product) {
        Product created = productService.create(product);
        return ResponseEntity.status(201).body(new ApiResponse<>("Product created successfully", created));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> update(@PathVariable Long id, @RequestBody Product product) {
        Product updated = productService.update(id, product);
        return ResponseEntity.ok(new ApiResponse<>("Product updated successfully", updated));
    }
    @PutMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<ApiResponse<Product>> assignCategory(@PathVariable Long productId, @PathVariable Long categoryId) {
        Product updated = productService.assignCategory(productId, categoryId);
        return ResponseEntity.ok(new ApiResponse<>("Category assigned successfully", updated));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("Product deleted successfully"));
    }
}
