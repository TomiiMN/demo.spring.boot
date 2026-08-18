package com.example.demo;

import com.example.demo.dto.CategoryRequestDTO;
import com.example.demo.dto.CategoryResponseDTO;
import com.example.demo.dto.ProductRequestDTO;
import com.example.demo.dto.ProductResponseDTO;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(CategoryService categoryService, ProductService productService) {
        return args -> {
            CategoryResponseDTO perifericos = categoryService.create(new CategoryRequestDTO("Perifericos"));
            CategoryResponseDTO procesadores = categoryService.create(new CategoryRequestDTO("Procesadores"));

            ProductResponseDTO mouse = productService.create(new ProductRequestDTO("Mouse", 1500.0, 1L));
            ProductResponseDTO teclado = productService.create(new ProductRequestDTO("Teclado", 3200.0, 1L));
            ProductResponseDTO amd = productService.create(new ProductRequestDTO("AMD Ryzen 7", 45000.0, 2L));

            productService.assignCategory(mouse.getId(), perifericos.getId());
            productService.assignCategory(teclado.getId(), perifericos.getId());
            productService.assignCategory(amd.getId(), procesadores.getId());

            System.out.println("Datos de prueba cargados correctamente");
        };
    }
}
