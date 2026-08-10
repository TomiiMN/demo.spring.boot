package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAll() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(new ApiResponse<>("Categories retrieved successfully", categories));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>("Category retrieved successfully", category));
    }
    @GetMapping("/{id}/products")
    public ResponseEntity<ApiResponse<List<Product>>> getCategoriesProducts(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        List<Product> categoriesProducts = category.getProducts();
        return ResponseEntity.ok(new ApiResponse<>("Categories products retrieved successfully", categoriesProducts));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<Category>> create(@RequestBody Category category) {
        Category created = categoryService.create(category);
        return ResponseEntity.status(201).body(new ApiResponse<>("Category created successfully", created));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> update(@PathVariable Long id, @RequestBody Category category) {
        Category updated = categoryService.update(id, category);
        return ResponseEntity.ok(new ApiResponse<>("Category updated successfully", updated));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("Category deleted successfully"));
    }
}
