package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.CategoryRequestDTO;
import com.example.demo.dto.CategoryResponseDTO;
import com.example.demo.dto.ProductResponseDTO;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getAll() {
        List<CategoryResponseDTO> categories = categoryService.findAll();
        return ResponseEntity.ok(new ApiResponse<>("Categories retrieved successfully", categories));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> getById(@PathVariable Long id) {
        CategoryResponseDTO category = categoryService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>("Category retrieved successfully", category));
    }
    @GetMapping("/{id}/products")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getCategoriesProducts(@PathVariable Long id) {
        List<ProductResponseDTO> categoryProducts = productService.findByCategoryId(id);
        return ResponseEntity.ok(new ApiResponse<>("Categories products retrieved successfully", categoryProducts));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> create(@RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO created = categoryService.create(dto);
        return ResponseEntity.status(201).body(new ApiResponse<>("Category created successfully", created));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> update(@PathVariable Long id, @RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO updated = categoryService.update(id, dto);
        return ResponseEntity.ok(new ApiResponse<>("Category updated successfully", updated));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("Category deleted successfully"));
    }
}
