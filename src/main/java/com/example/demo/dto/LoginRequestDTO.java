package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {

    @NotBlank(message = "El email no puede estar vacío")
    private final String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private final String password;

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
