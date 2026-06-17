package com.tutor.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

import com.tutor.config.JwtProvider;
import com.tutor.entity.Admin;
import com.tutor.entity.SuperAdmin;
import com.tutor.entity.Teacher;
import com.tutor.entity.User;
import com.tutor.enums.Role;
import com.tutor.repository.AdminRepository;
import com.tutor.repository.SuperAdminRepository;
import com.tutor.repository.TeacherRepository;
import com.tutor.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private SuperAdminRepository superAdminRepository;
	
	

	// ===============================
	// CREATE ADMIN (SUPER ADMIN ONLY)
	// ===============================
	@Override
	public ResponseEntity<?> createAdmin(Admin admin, Long superAdminId, HttpServletRequest request) {

		// 🔐 Authorization Header Check
		if (request.getHeader("Authorization") == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authenticated");
		}

		String token = request.getHeader("Authorization").substring(7);
		Claims claims = jwtProvider.getPayload(token);

		String email = claims.getSubject(); // ✅ EMAIL stored in JWT

		// 🔐 Logged-in Super Admin
		SuperAdmin loggedInSuperAdmin = superAdminRepository.findByEmail(email)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Super Admin not found"));

		if (loggedInSuperAdmin.getRole() != Role.SUPER_ADMIN) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
		}

		// 🔎 Super Admin from path variable
		SuperAdmin creator = superAdminRepository.findById(superAdminId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Super Admin ID not found"));
		
		String encodedPassword = passwordEncoder.encode(admin.getPassword());


		// ✅ CREATE ADMIN (IMPORTANT FIXES HERE)
		Admin newAdmin = new Admin();
		newAdmin.setFullName(admin.getFullName());
		newAdmin.setEmail(admin.getEmail());
		newAdmin.setPassword(encodedPassword);
		newAdmin.setPhone(admin.getPhone());
		newAdmin.setUserRole(Role.ADMIN);
		newAdmin.setCreatedBy(creator); // ⭐⭐⭐ REQUIRED FIX
		newAdmin.setCreatedAt(LocalDateTime.now().withNano(0));
		newAdmin.setUpdatedAt(LocalDateTime.now().withNano(0));
		
		// ========================= USER TABLE =========================
        User user = new User();
        user.setName(admin.getFullName());
        user.setPassword(encodedPassword);
        user.setEmail(admin.getEmail());
        user.setRole(Role.ADMIN);
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(admin.getPassword()));
        user.setFirstLogin(true);
        userRepository.save(user);
		return ResponseEntity.ok(adminRepository.save(newAdmin));
	}

	// ===============================
	// GET ALL TEACHERS (ADMIN ONLY)
	// ===============================
	@Override
	public List<Teacher> getEntireTeachers(HttpServletRequest request) {

		String token = extractBearerToken(request);
		Claims claims = jwtProvider.getPayload(token);

		String email = claims.getSubject();

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

		if (user.getRole() != Role.ADMIN) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
		}

		return teacherRepository.findAll();
	}

	// ===============================
	// GET ALL STUDENTS (ADMIN ONLY)
	// ===============================
	@Override
	public List<ResponseEntity<?>> getEntireStudents(HttpServletRequest request) {

		String token = extractBearerToken(request);
		Claims claims = jwtProvider.getPayload(token);

		String email = claims.getSubject();

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

		if (user.getRole() != Role.ADMIN) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
		}

		return List.of(); // TODO: return students
	}

	// ===============================
	// TOKEN HELPERS
	// ===============================
	private String extractBearerToken(HttpServletRequest request) {

		String header = request.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
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

		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authentication cookie");
	}

//	@Override
	public ResponseEntity<?> uploadProfilePicture(Long adminId, MultipartFile file) {
		 Admin admin = adminRepository.findById(adminId)
		            .orElseThrow(() -> new RuntimeException("Admin not found"));

		    try {

		        String uploadDir = "uploads/admins/";

		        File directory = new File(uploadDir);
		        if (!directory.exists()) {
		            directory.mkdirs();
		        }

		        String fileName = "admin_" + adminId + "_" + file.getOriginalFilename();
		        String filePath = uploadDir + fileName;

		        File dest = new File(filePath);
		        file.transferTo(dest);

		        admin.setProfilePicture("/" + filePath);

		        adminRepository.save(admin);

		        return ResponseEntity.ok("Profile picture uploaded successfully");

		    } catch (IOException e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                .body("File upload failed");
		    }
		}
	
}
