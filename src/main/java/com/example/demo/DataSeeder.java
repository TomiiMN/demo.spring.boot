package com.example.demo;

import com.example.demo.dto.CategoryRequestDTO;
import com.example.demo.dto.CategoryResponseDTO;
import com.example.demo.dto.ProductRequestDTO;
import com.example.demo.dto.ProductResponseDTO;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(CategoryService categoryService, ProductService productService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            CategoryResponseDTO perifericos = categoryService.create(new CategoryRequestDTO("Perifericos"));
            CategoryResponseDTO procesadores = categoryService.create(new CategoryRequestDTO("Procesadores"));

            ProductResponseDTO mouse = productService.create(new ProductRequestDTO("Mouse", 1500.0, 1L));
            ProductResponseDTO teclado = productService.create(new ProductRequestDTO("Teclado", 3200.0, 1L));
            ProductResponseDTO amd = productService.create(new ProductRequestDTO("AMD Ryzen 7", 45000.0, 2L));

            productService.assignCategory(mouse.getId(), perifericos.getId());
            productService.assignCategory(teclado.getId(), perifericos.getId());
            productService.assignCategory(amd.getId(), procesadores.getId());

            User admin = new User("admin@demo.com", passwordEncoder.encode("admin1234"), Role.ADMIN);
            userRepository.save(admin);
            User regularUser = new User("user@demo.com", passwordEncoder.encode("user1234"), Role.USER);
            userRepository.save(regularUser);


            System.out.println("Datos de prueba cargados correctamente");
            System.out.println("Admin: admin@demo.com / admin1234");
            System.out.println("User:  user@demo.com / user1234");
        };
    }
}
