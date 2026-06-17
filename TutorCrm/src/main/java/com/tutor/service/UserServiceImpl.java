package com.tutor.service;

import com.tutor.entity.User;
import com.tutor.enums.Role;
import com.tutor.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;  // ✅ Add this

    // REGISTER USER
    @Override
    public User registerUser(String name,
                             String email,
                             String password,
                             Role role) {

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("User with this email already exists");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);

        // 🔐 Encode password before saving
        user.setPassword(passwordEncoder.encode(password));

        user.setRole(role != null ? role : Role.STUDENT);
        user.setCreatedAt(LocalDateTime.now());
        user.setFirstLogin(true);

        return userRepository.save(user);
    }

    // =========================
    // LOGIN USER
    // =========================
    @Override
    public Optional<User> loginUser(String email,
                                    String password) {

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {

            User user = userOpt.get();

            // 🔐 Compare using matches()
            if (passwordEncoder.matches(password, user.getPassword())) {

                emailService.sendLoginEmail(
                        user.getEmail(),
                        user.getName(),
                        user.getRole().name(),
                        user.isFirstLogin()
                );

                if (user.isFirstLogin()) {
                    user.setFirstLogin(false);
                    userRepository.save(user);
                }

                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    // =========================
    // FIND METHODS
    // =========================
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}