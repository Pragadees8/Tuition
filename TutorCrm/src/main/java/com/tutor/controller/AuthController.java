package com.tutor.controller;

import com.tutor.dto.LoginRequestDTO;
import com.tutor.dto.LoginResponseDTO;
import com.tutor.entity.User;
import com.tutor.enums.Role;
import com.tutor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        Optional<User> userOpt = userService.loginUser(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // For now, simple token; replace with JWT in production
            String token = UUID.randomUUID().toString();

            LoginResponseDTO response = new LoginResponseDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRole().name(), // convert enum to string
                    token,
                    "Login successful"
            );

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid email or password");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Ensure role is enum
        Role role = user.getRole() != null ? user.getRole() : Role.STUDENT;

        User createdUser = userService.registerUser(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                role
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
