package com.example.demo.service;

import com.example.demo.dto.ProductRequestDTO;
import com.example.demo.dto.ProductResponseDTO;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    //findById
    @Test
    void shouldReturnProductWhenIdExists() {
        //Arrange
        Product product = new Product("Laptop", 1500.0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        //Act
        ProductResponseDTO result = productService.findById(1L);

        //Assert
        assertEquals("Laptop", result.getName());
        assertEquals(1500.0, result.getPrice());
    }

    @Test
    void shouldReturnExceptionWhenProductDoNotExist() {
        //Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> productService.findById(1L));
    }

    //create
    @Test
    void shouldReturnProductWithoutCategory() {
        //Arrange
        ProductRequestDTO dto = new ProductRequestDTO("Mouse", 25.0, null);
        Product savedProduct = new Product("Mouse", 25.0);
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        //Act
        ProductResponseDTO result = productService.create(dto);

        //Assert
        assertEquals("Mouse", result.getName());
        assertEquals(25.0, result.getPrice());
        assertNull(result.getCategoryName());
        verify(categoryService, never()).findEntityById(any());
    }

    @Test
    void shouldReturnProductWithCategory() {
        //Arrange
        ProductRequestDTO dto = new ProductRequestDTO("Keyboard", 45.0, 2L);
        Category category = new Category("Electronics");
        Product savedProduct = new Product("Keyboard", 45.0);
        savedProduct.setCategory(category);

        when(categoryService.findEntityById(2L)).thenReturn(category);
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        //Act
        ProductResponseDTO result = productService.create(dto);

        //Assert
        assertEquals("Keyboard", result.getName());
        assertEquals(45.0, result.getPrice());
        assertEquals("Electronics", result.getCategoryName());
    }

    //update
    @Test
    void shouldReturnUpdatedProductWithoutCategory() {
        //Arrange
        ProductRequestDTO dto = new ProductRequestDTO("Keyboard", 300.0, null);
        Product existing = new Product("Old Keyboard", 250.0);
        Product savedProduct = new Product("Keyboard", 300.0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.save(existing)).thenReturn(savedProduct);

        //Act
        ProductResponseDTO result = productService.update(1L, dto);

        //Assert
        assertEquals("Keyboard", result.getName());
        assertEquals(300.0, result.getPrice());
        assertNull(result.getCategoryName());
        verify(categoryService, never()).findEntityById(any());
    }

    @Test
    void shouldReturnUpdatedProductWithCategory() {
        //Arrange
        ProductRequestDTO dto = new ProductRequestDTO("Keyboard", 300.0, 3L);
        Product existing = new Product("Old Keyboard", 250.0);
        Category category = new Category("Hardware");
        Product savedProduct = new Product("Keyboard", 300.0);
        savedProduct.setCategory(category);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.save(existing)).thenReturn(savedProduct);
        when(categoryService.findEntityById(3L)).thenReturn(category);

        //Act
        ProductResponseDTO result = productService.update(1L, dto);

        //Assert
        assertEquals("Keyboard", result.getName());
        assertEquals(300.0, result.getPrice());
        assertEquals("Hardware", result.getCategoryName());
    }

    //delete
    @Test
    void shouldDeleteProductWhenIdExists() {
        //Arrange
        Product product = new Product("Keyboard", 300.0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        //Act
        productService.delete(1L);
        //Assert
        verify(productRepository).delete(product);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentProduct() {
        //Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> productService.delete(1L));
        verify(productRepository, never()).delete(any());
    }
}
