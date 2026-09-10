package com.example.demo.service;

import com.example.demo.dto.ProductResponseDTO;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldReturnProductWhenIdExists() {
        Product product = new Product("Laptop", 1500.0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        ProductResponseDTO result = productService.findById(1L);
        assertEquals("Laptop", result.getName());
        assertEquals(1500.0, result.getPrice());
    }

    @Test
    void shouldReturnExceptionWhenProductIsEmpty() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> productService.findById(1L));
    }
}
