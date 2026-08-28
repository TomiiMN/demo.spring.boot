package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.ProductRequestDTO;
import com.example.demo.dto.ProductResponseDTO;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Gestión de productos del catálogo")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Operation(summary = "Listar todos los productos")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAll() {
        List<ProductResponseDTO> products = productService.findAll();
        return ResponseEntity.ok(new ApiResponse<>("Products retrieved successfully", products));
    }

    @Operation(summary = "Buscar un producto por id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getById(@PathVariable Long id) {
        ProductResponseDTO product = productService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>("Product retrieved successfully", product));
    }

    @Operation(summary = "Listar productos con menor precio al indicado")
    @GetMapping("/price/{price}")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getByPriceLessThan(@PathVariable double price) {
        List<ProductResponseDTO> products = productService.findByPriceLessThan(price);
        return ResponseEntity.ok(new ApiResponse<>("Products retrieved successfully", products));
    }

    @Operation(summary = "Listar productos por nombre de categoría")
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getByCategoryName(@PathVariable String categoryName) {
        List<ProductResponseDTO> products = productService.findByCategoryName(categoryName);
        return ResponseEntity.ok(new ApiResponse<>("Products retrieved successfully", products));
    }

    @Operation(summary = "Crear un producto", description = "Valida los datos y opcionalmente lo asocia a una categoría existente")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDTO>> create(@Valid @RequestBody ProductRequestDTO product) {
        ProductResponseDTO created = productService.create(product);
        return ResponseEntity.status(201).body(new ApiResponse<>("Product created successfully", created));
    }

    @Operation(summary = "Actualizar un producto existente")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO product) {
        ProductResponseDTO updated = productService.update(id, product);
        return ResponseEntity.ok(new ApiResponse<>("Product updated successfully", updated));
    }

    @Operation(summary = "Asignar o cambiar la categoría de un producto")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> assignCategory(@PathVariable Long productId, @PathVariable Long categoryId) {
        ProductResponseDTO updated = productService.assignCategory(productId, categoryId);
        return ResponseEntity.ok(new ApiResponse<>("Category assigned successfully", updated));
    }

    @Operation(summary = "Eliminar un producto")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("Product deleted successfully"));
    }
}
