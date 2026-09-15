package com.example.demo.service;

import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    //login
    @Test
    void shouldReturnTokenWhenCredentialsAreValid() {
        //Arrange
        LoginRequestDTO dto = new LoginRequestDTO("user@demo.com", "user1234");
        User user = new User("user@demo.com", "hashedPassword123", Role.USER);
        when(userRepository.findByEmail("user@demo.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("user1234", "hashedPassword123")).thenReturn(true);
        when(jwtService.generateToken("user@demo.com")).thenReturn("fake-jwt-token");
        //Act
        AuthResponseDTO result = authService.login(dto);
        //Assert
        assertEquals("fake-jwt-token", result.getToken());
    }

    @Test
    void shouldThrowExceptionWhenEmailDoNotExist() {
        LoginRequestDTO dto = new LoginRequestDTO("user@demo.com", "user1234");
        when(userRepository.findByEmail("user@demo.com")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> authService.login(dto));
        verify(passwordEncoder, never()).matches(any(), any());
        verify(jwtService, never()).generateToken("user@demo.com");
    }

    @Test
    void shouldThrowExceptionWhenPasswordDoNotMatch() {
        //Arrange
        LoginRequestDTO dto = new LoginRequestDTO("user@demo.com", "user1234");
        User user = new User("user@demo.com", "hashedPassword123", Role.USER);
        when(userRepository.findByEmail("user@demo.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("user1234", "hashedPassword123")).thenReturn(false);
        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> authService.login(dto));
        verify(jwtService, never()).generateToken("user@demo.com");
    }

    //register
    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        //Arrange
        User user = new User("user@demo.com", "hashedPassword123", Role.USER);
        RegisterRequestDTO dto = new RegisterRequestDTO("user@demo.com", "user1234");
        when(userRepository.findByEmail("user@demo.com")).thenReturn(Optional.of(user));
        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> authService.register(dto));
        verify(passwordEncoder, never()).encode("user1234");
    }

    @Test
    void shouldReturnUserWhenEmailDoNotExist() {
        //Arrange
        RegisterRequestDTO dto = new RegisterRequestDTO("user@demo.com", "user1234");
        User user = new User("user@demo.com", "hashedPassword123", Role.USER);
        when(userRepository.findByEmail("user@demo.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("user1234")).thenReturn("hashedPassword123");
        when(userRepository.save(any(User.class))).thenReturn(user);
        //Act
        UserResponseDTO result = authService.register(dto);
        //Assert
        assertNull(result.getId());
        assertEquals("user@demo.com", result.getEmail());
        assertEquals("USER", result.getRole());
    }
}
