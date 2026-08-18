package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.ProductRequestDTO;
import com.example.demo.dto.ProductResponseDTO;
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
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAll() {
        List<ProductResponseDTO> products = productService.findAll();
        return ResponseEntity.ok(new ApiResponse<>("Products retrieved successfully", products));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getById(@PathVariable Long id) {
        ProductResponseDTO product = productService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>("Product retrieved successfully", product));
    }
    @GetMapping("/price/{price}")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getByPriceLessThan(@PathVariable double price) {
        List<ProductResponseDTO> products = productService.findByPriceLessThan(price);
        return ResponseEntity.ok(new ApiResponse<>("Products retrieved successfully", products));
    }
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getByCategoryName(@PathVariable String categoryName) {
        List<ProductResponseDTO> products = productService.findByCategoryName(categoryName);
        return ResponseEntity.ok(new ApiResponse<>("Products retrieved successfully", products));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDTO>> create(@RequestBody ProductRequestDTO product) {
        ProductResponseDTO created = productService.create(product);
        return ResponseEntity.status(201).body(new ApiResponse<>("Product created successfully", created));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> update(@PathVariable Long id, @RequestBody ProductRequestDTO product) {
        ProductResponseDTO updated = productService.update(id, product);
        return ResponseEntity.ok(new ApiResponse<>("Product updated successfully", updated));
    }
    @PutMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> assignCategory(@PathVariable Long productId, @PathVariable Long categoryId) {
        ProductResponseDTO updated = productService.assignCategory(productId, categoryId);
        return ResponseEntity.ok(new ApiResponse<>("Category assigned successfully", updated));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("Product deleted successfully"));
    }
}
