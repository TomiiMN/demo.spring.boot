package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }
    public Product create(Product product) {
        return productRepository.save(product);
    }
    public Product update(Long id, Product updatedProduct) {
        Product existing = findById(id);
        existing.setName(updatedProduct.getName());
        existing.setPrice(updatedProduct.getPrice());
        existing.setCategory(updatedProduct.getCategory());
        return productRepository.save(existing);
    }
    public void delete(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }
    public Product assignCategory(Long productId, Long categoryId){
        Product product = findById(productId);
        Category category = categoryService.findById(categoryId);
        product.setCategory(category);
        return productRepository.save(product);
    }
}
