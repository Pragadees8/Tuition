package com.tutor.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import com.tutor.config.JwtProvider;
import com.tutor.entity.SubAdmin;
import com.tutor.entity.SuperAdmin;
import com.tutor.entity.User;
import com.tutor.enums.Role;
import com.tutor.repository.SubAdminRepository;
import com.tutor.repository.SuperAdminRepository;
import com.tutor.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SubAdminServiceImpl implements SubAdminService {

    @Autowired
    private SubAdminRepository subAdminRepo;

    @Autowired
    private SuperAdminRepository superAdminRepo;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder; 

    @Autowired
    private JwtProvider jwtProvider;

    // ===============================
    // CREATE SUB ADMIN
    // ===============================
    @Override
    public SubAdmin createSubAdmin(SubAdmin subAdmin, Long superAdminId) {

        // 1️⃣ Validate super admin
        SuperAdmin superAdmin = superAdminRepo.findById(superAdminId)
                .orElseThrow(() -> new RuntimeException("SuperAdmin not found"));

        // 2️⃣ Check duplicate sub-admin by email
        Optional<SubAdmin> existingSubAdmin = subAdminRepo.findByEmail(subAdmin.getEmail());
        if (existingSubAdmin.isPresent()) {
            throw new RuntimeException("Sub Admin already exists with this email");
        }

        // 3️⃣ Create new SubAdmin and set all fields
        SubAdmin newSubAdmin = new SubAdmin();
        newSubAdmin.setName(subAdmin.getName());
        newSubAdmin.setEmail(subAdmin.getEmail());
        newSubAdmin.setPassword(subAdmin.getPassword()); // TODO: hash if needed
        newSubAdmin.setPhoneNumber(subAdmin.getPhoneNumber());
        newSubAdmin.setRole(Role.SUB_ADMIN);
        newSubAdmin.setCreatedAt(LocalDateTime.now());
        newSubAdmin.setUpdatedAt(LocalDateTime.now());

        // ✅ Set the creator (foreign key)
        newSubAdmin.setCreatedBy(superAdmin);
        
     // ========================= USER TABLE =========================
        User user = new User();
        user.setName(subAdmin.getName());
        user.setEmail(subAdmin.getEmail());
        user.setRole(Role.SUB_ADMIN);
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(subAdmin.getPassword()));
        user.setFirstLogin(true);
        userRepository.save(user);

        // 4️⃣ Save to DB
        return subAdminRepo.save(newSubAdmin);
    }

    // ===============================
    // GET ALL SUB ADMINS (JWT BASED)
    // ===============================
    @Override
    public List<SubAdmin> getAllSubAdmins(HttpServletRequest request) {

        String token = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("access_token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            throw new RuntimeException("You are not authenticated");
        }

        Claims claims = jwtProvider.getPayload(token);
        String email = claims.getSubject(); // ✅ EMAIL, NOT FULL NAME

        // Validate logged-in super admin
        superAdminRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Super Admin not found"));

        return subAdminRepo.findAll();
    }

    // ===============================
    // GET SUB ADMIN BY ID
    // ===============================
    @Override
    public SubAdmin getSubAdminById(Long id, Long superAdminId) {

        superAdminRepo.findById(superAdminId)
                .orElseThrow(() -> new RuntimeException("SuperAdmin not found"));

        return subAdminRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("SubAdmin not found"));
    }

    // ===============================
    // UPDATE SUB ADMIN
    // ===============================
    @Override
    public SubAdmin updateSubAdmin(Long id, SubAdmin details, Long superAdminId) {

        superAdminRepo.findById(superAdminId)
                .orElseThrow(() -> new RuntimeException("SuperAdmin not found"));

        SubAdmin existing = getSubAdminById(id, superAdminId);

        existing.setName(details.getName());
        existing.setEmail(details.getEmail());
        existing.setPhoneNumber(details.getPhoneNumber());
        existing.setPassword(details.getPassword()); // TODO: hash if needed
        existing.setUpdatedAt(LocalDateTime.now());

        return subAdminRepo.save(existing);
    }

    // ===============================
    // DELETE SUB ADMIN
    // ===============================
    @Override
    public void deleteSubAdmin(Long id, Long superAdminId) {

        superAdminRepo.findById(superAdminId)
                .orElseThrow(() -> new RuntimeException("SuperAdmin not found"));

        SubAdmin subAdmin = getSubAdminById(id, superAdminId);
        subAdminRepo.delete(subAdmin);
    }
    @Override
    public ResponseEntity<?> uploadProfilePicture(Long subAdminId, MultipartFile file, HttpServletRequest request) {

        SubAdmin subAdmin = subAdminRepo.findById(subAdminId)
                .orElseThrow(() -> new RuntimeException("SubAdmin not found"));

        try {

            String uploadDir = "uploads/subadmins/";

            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = "subadmin_" + subAdminId + "_" + file.getOriginalFilename();
            String filePath = uploadDir + fileName;

            File dest = new File(filePath);
            file.transferTo(dest);

            subAdmin.setProfilePicture("/" + filePath);

            subAdminRepo.save(subAdmin);

            return ResponseEntity.ok("Profile picture uploaded successfully");

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Upload failed");
        }
    }
    
}
