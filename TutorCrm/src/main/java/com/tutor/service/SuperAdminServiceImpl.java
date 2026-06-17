package com.tutor.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import com.tutor.config.JwtProvider;
import com.tutor.entity.Admin;
import com.tutor.entity.SuperAdmin;
import com.tutor.entity.User;
import com.tutor.enums.Role;
import com.tutor.repository.AdminRepository;
import com.tutor.repository.SubAdminRepository;
import com.tutor.repository.SuperAdminRepository;
import com.tutor.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SuperAdminServiceImpl implements SuperAdminService {

    @Autowired
    private SuperAdminRepository superAdminRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private SubAdminRepository subAdminRepository;
    	
    @Autowired
    private UserRepository userRepository;
    
 
    // ===============================
    // CREATE SUPER ADMIN
    // ===============================
    public SuperAdmin create(SuperAdmin superAdmin) {

        if (superAdminRepository.existsByEmail(superAdmin.getEmail())) {
            throw new RuntimeException("Email address is already registered");
        }
        superAdmin.setFullName(superAdmin.getFullName());
        superAdmin.setRole(Role.SUPER_ADMIN);
        superAdmin.setPassword(passwordEncoder.encode(superAdmin.getPassword()));
        
     // ========================= USER TABLE =========================
        User user = new User();
        user.setName(superAdmin.getFullName());
        user.setEmail(superAdmin.getEmail());
        user.setRole(Role.SUPER_ADMIN);
        user.setCreatedAt(LocalDateTime.now());
        user.setFirstLogin(true);
        user.setPassword(passwordEncoder.encode(superAdmin.getPassword()));
        userRepository.save(user);

        return superAdminRepository.save(superAdmin);
    }

    // ===============================
    // GET ALL SUPER ADMINS
    // ===============================
    public List<SuperAdmin> getAll(HttpServletRequest request) {

        String token = extractBearerToken(request);
        Claims payload = jwtProvider.getPayload(token);

        String email = payload.getSubject(); // ✅ EMAIL

        SuperAdmin loginAdmin = superAdminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Super Admin Not Found"));

        if (loginAdmin.getRole() != Role.SUPER_ADMIN) {
            throw new RuntimeException("You don't have access to this page");
        }

        return superAdminRepository.findAll();
    }

    // LOGIN
    @Override
    public ResponseEntity<?> login(SuperAdmin user, HttpServletResponse response) {

        SuperAdmin existingUser = superAdminRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return ResponseEntity.badRequest().body("Wrong credentials");
        }
        
       
        // ✅ subject = email
        String token = jwtProvider.generateToken(
        		 existingUser.getSuperAdminId(),   
                existingUser.getEmail(),
                existingUser.getRole().name()
        );

        return ResponseEntity.ok(token);
    }

    // GET LOGGED-IN SUPER ADMIN
    public ResponseEntity<?> getLoginSuperAdmin(HttpServletRequest request) {

        String token = extractBearerToken(request);
        Claims payload = jwtProvider.getPayload(token);

        String email = payload.getSubject();

        SuperAdmin existingUser = superAdminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Super Admin not Found"));

        if (existingUser.getRole() != Role.SUPER_ADMIN) {
            throw new RuntimeException("Not allowed");
        }

        return ResponseEntity.ok(existingUser);
    }

    // ===============================
    // UPDATE SUPER ADMIN
    // ===============================
    public SuperAdmin update(Long id, SuperAdmin updated, HttpServletRequest request) {

        String token = extractBearerToken(request);
        Claims payload = jwtProvider.getPayload(token);

        String email = payload.getSubject();

        SuperAdmin existingUser = superAdminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Super Admin not Found"));

        if (existingUser.getRole() != Role.SUPER_ADMIN) {
            throw new RuntimeException("Not allowed");
        }

        if (!existingUser.getSuperAdminId().equals(id)) {
            throw new RuntimeException("You cannot update another account");
        }

        if (!existingUser.getEmail().equals(updated.getEmail())
                && superAdminRepository.existsByEmail(updated.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        if (updated.getFullName() != null && !updated.getFullName().isEmpty()) {
            existingUser.setFullName(updated.getFullName());
        }

        if (updated.getEmail() != null && !updated.getEmail().isEmpty()) {
            existingUser.setEmail(updated.getEmail());
        }

        if (updated.getPassword() != null && !updated.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updated.getPassword()));
        }

        return superAdminRepository.save(existingUser);
    }

    // ===============================
    // DELETE SUPER ADMIN
    // ===============================
    public void delete(Long superAdminId, HttpServletRequest request) {

        String token = extractBearerToken(request);
        Claims payload = jwtProvider.getPayload(token);

        String email = payload.getSubject();

        SuperAdmin existingUser = superAdminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Super Admin not Found"));

        if (!existingUser.getSuperAdminId().equals(superAdminId)) {
            throw new RuntimeException("You cannot delete another account");
        }

        if (superAdminRepository.count() <= 1) {
            throw new RuntimeException("Cannot delete the last SUPER_ADMIN");
        }

        superAdminRepository.deleteById(superAdminId);
    }

    // ===============================
    // COUNTS
    // ===============================
    @Override
    public ResponseEntity<?> countAdmin() {
        return ResponseEntity.ok(adminRepository.count());
    }

    @Override
    public ResponseEntity<?> countSubAdmin() {
        return ResponseEntity.ok(subAdminRepository.count());
    }

    // ===============================
    // GET ALL ADMINS
    // ===============================
    @Override
    public ResponseEntity<List<?>> getAllAdmins(HttpServletRequest request) {

        String token = extractCookieToken(request);
        Claims payload = jwtProvider.getPayload(token);

        String email = payload.getSubject();

        superAdminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Super Admin not Found"));

        return ResponseEntity.ok(adminRepository.findAll());
    }

    // ===============================
    // TOKEN HELPERS
    // ===============================
    private String extractBearerToken(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("You are not authenticated");
        }

        return header.substring(7);
    }

    private String extractCookieToken(HttpServletRequest request) {

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("access_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        throw new RuntimeException("You are not authenticated");
    }
    
    @Override
    public ResponseEntity<?> uploadProfilePicture(Long superAdminId, MultipartFile file, HttpServletRequest request) {

        String token = extractBearerToken(request);
        Claims payload = jwtProvider.getPayload(token);

        String email = payload.getSubject();

        SuperAdmin loggedIn = superAdminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Super Admin not found"));

        if (!loggedIn.getSuperAdminId().equals(superAdminId)) {
            throw new RuntimeException("You cannot update another profile");
        }

        SuperAdmin superAdmin = superAdminRepository.findById(superAdminId)
                .orElseThrow(() -> new RuntimeException("Super Admin not found"));

        try {

            String uploadDir = "uploads/superadmins/";

            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = "superadmin_" + superAdminId + "_" + file.getOriginalFilename();
            String filePath = uploadDir + fileName;

            File dest = new File(filePath);
            file.transferTo(dest);

            superAdmin.setProfilePicture("/" + filePath);

            superAdminRepository.save(superAdmin);

            return ResponseEntity.ok("Profile picture uploaded successfully");

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Upload failed");
        }
    }
}
