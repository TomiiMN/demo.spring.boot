package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public UserResponseDTO register(RegisterRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            logger.warn("Register fallido: email ya en uso: ({})", dto.getEmail());
            throw new IllegalArgumentException("Email already in use");
        }
        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        User user = new User(dto.getEmail(), hashedPassword, Role.USER);
        User saved = userRepository.save(user);
        logger.info("Register exitoso: {}", dto.getEmail());
        return new UserResponseDTO(saved.getId(), saved.getUsername(), saved.getRole().name());
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> {
                    logger.warn("Login fallido: email no encontrado: ({})", dto.getEmail());
                    return new IllegalArgumentException("Invalid credentials");
                });

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            logger.warn("Login fallido: contraseña incorrecta para {}", dto.getEmail());
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUsername());
        logger.info("Login exitoso: {}", dto.getEmail());
        return new AuthResponseDTO(token);
    }
}
