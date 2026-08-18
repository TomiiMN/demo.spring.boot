package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductRequestDTO;
import com.example.demo.dto.ProductResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    public ProductResponseDTO toResponseDTO(Product product) {
        String categoryName = null;
        if (product.getCategory() != null) {
            categoryName = product.getCategory().getName();
        }

        return new ProductResponseDTO(
          product.getId(),
          product.getName(),
          product.getPrice(),
          categoryName
        );
    }

    public Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return product;
    }

    public void updateEntityFromDTO(ProductRequestDTO dto, Product existingProduct) {
        existingProduct.setName(dto.getName());
        existingProduct.setPrice(dto.getPrice());
    }

    public List<ProductResponseDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return toResponseDTO(product);
    }
    public List<ProductResponseDTO> findByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    public List<ProductResponseDTO> findByPriceLessThan(double price) {
        List<Product> products = productRepository.findByPriceLessThan(price);
        return products.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    public List<ProductResponseDTO> findByCategoryName(String categoryName) {
        List<Product> products = productRepository.findByCategoryName(categoryName);
        return products.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    public ProductResponseDTO create(ProductRequestDTO dto) {
        Product product = toEntity(dto);

        if (dto.getCategoryId() != null) {
            Category category = categoryService.findEntityById(dto.getCategoryId());
            product.setCategory(category);
        }

        Product saved = productRepository.save(product);
        return toResponseDTO(saved);
    }
    public ProductResponseDTO update(Long id, ProductRequestDTO updatedProductDto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        updateEntityFromDTO(updatedProductDto, existing);
        if (updatedProductDto.getCategoryId() != null) {
            Category category = categoryService.findEntityById(updatedProductDto.getCategoryId());
            existing.setCategory(category);
        }
        Product saved = productRepository.save(existing);
        return toResponseDTO(saved);
    }
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        productRepository.delete(product);
    }
    public ProductResponseDTO assignCategory(Long productId, Long categoryId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        Category category = categoryService.findEntityById(categoryId);
        product.setCategory(category);
        Product saved = productRepository.save(product);
        return toResponseDTO(saved);
    }

}
