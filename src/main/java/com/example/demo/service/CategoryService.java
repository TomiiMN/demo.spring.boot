package com.example.demo.service;

import com.example.demo.dto.CategoryRequestDTO;
import com.example.demo.dto.CategoryResponseDTO;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponseDTO toResponseDTO(Category category) {
        List<String> productNames = category.getProducts().stream()
                .map(Product::getName)
                .collect(Collectors.toList());

        return new CategoryResponseDTO(category.getId(), category.getName(), productNames);
    }

    public Category toEntity(CategoryRequestDTO dto) {
        return new Category(dto.getName());
    }

    public Category findEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    public CategoryResponseDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        return toResponseDTO(category);
    }
    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        Category saved = categoryRepository.save(toEntity(dto));
        return toResponseDTO(saved);
    }
    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {
        Category existing = findEntityById(id);
        existing.setName(dto.getName());
        Category saved = categoryRepository.save(existing);
        return toResponseDTO(saved);
    }
    public void delete(Long id) {
        Category category = findEntityById(id);
        categoryRepository.delete(category);
    }

}
