package com.tutor.service;

import com.tutor.dto.StudentRequestDTO;
import com.tutor.dto.StudentResponseDTO;
import com.tutor.entity.Admin;
import com.tutor.entity.Student;
import com.tutor.entity.User;
import com.tutor.enums.Role;
import com.tutor.enums.Section;
import com.tutor.repository.AdminRepository;
import com.tutor.repository.StudentRepository;
import com.tutor.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// =========================
	// GENERATE ROLL NUMBER
	// =========================
	private String generateRollNumber() {

		int year = Year.now().getValue();

		String prefix = year + "-STU-";

		long count = studentRepository.countByRollNumberStartingWith(prefix) + 1;

		return prefix + String.format("%03d", count);
	}

	// =========================
	// CREATE STUDENT
	// =========================
	@Override
	public StudentResponseDTO createStudent(StudentRequestDTO dto, Long adminId) {

		Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new RuntimeException("Admin not found"));

		if (studentRepository.existsByEmail(dto.getEmail()))
			throw new RuntimeException("Email already exists");

		if (studentRepository.existsByMobile(dto.getMobile()))
			throw new RuntimeException("Mobile already exists");

		if (studentRepository.existsByParentMobile(dto.getParentMobile()))
			throw new RuntimeException("Parent mobile already exists");

		String encodedPassword = passwordEncoder.encode(dto.getPassword());

		String rollNumber = generateRollNumber();

		Student studentRollno = new Student();
		Student student = new Student();
		student.setRollNumber(rollNumber);
		student.setFullName(dto.getFullName());
		student.setEmail(dto.getEmail());
		student.setPassword(encodedPassword);
		student.setMobile(dto.getMobile());
		student.setUserRole(Role.STUDENT);
		student.setParentName(dto.getParentName());
		student.setParentMobile(dto.getParentMobile());
		student.setAddress(dto.getAddress());
		student.setSection(dto.getSection());
		student.setAdmin(admin);

		Student savedStudent = studentRepository.save(student);

		// =========================
		// CREATE USER LOGIN
		// =========================
		User user = new User();
		user.setName(savedStudent.getFullName());
		user.setEmail(savedStudent.getEmail());
		user.setPassword(encodedPassword);
		user.setRole(Role.STUDENT);
		user.setCreatedAt(LocalDateTime.now());
		user.setRollNumber(rollNumber);
		user.setSection(dto.getSection());
		user.setFirstLogin(true);

		userRepository.save(user);

		return mapToResponse(savedStudent);
	}

	// =========================
	// GET ALL STUDENTS BY ADMIN
	// =========================
	@Override
	public List<StudentResponseDTO> getStudentsByAdmin(Long adminId) {

		return studentRepository.findByAdminAdminId(adminId).stream().map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	// =========================
	// GET SINGLE STUDENT
	// =========================
	@Override
	public StudentResponseDTO getStudentByAdmin(Long adminId, Long studentId) {

		Student student = studentRepository.findByIdAndAdminAdminId(studentId, adminId)
				.orElseThrow(() -> new RuntimeException("Student not found or access denied"));

		return mapToResponse(student);
	}

	// =========================
	// UPDATE STUDENT
	// =========================
	@Override
	public StudentResponseDTO updateStudentByAdmin(Long adminId, Long studentId, StudentRequestDTO dto) {

		Student student = studentRepository.findByIdAndAdminAdminId(studentId, adminId)
				.orElseThrow(() -> new RuntimeException("Student not found or access denied"));

		student.setFullName(dto.getFullName());
		student.setEmail(dto.getEmail());
		student.setMobile(dto.getMobile());
		student.setParentName(dto.getParentName());
		student.setParentMobile(dto.getParentMobile());
		student.setAddress(dto.getAddress());
		student.setSection(dto.getSection());

		Student updated = studentRepository.save(student);

		return mapToResponse(updated);
	}

	// =========================
	// DELETE STUDENT
	// =========================
	@Override
	public void deleteStudentByAdmin(Long adminId, Long studentId) {

		Student student = studentRepository.findByIdAndAdminAdminId(studentId, adminId)
				.orElseThrow(() -> new RuntimeException("Student not found or access denied"));

		studentRepository.delete(student);
	}

	// =========================
	// MAP ENTITY → DTO
	// =========================
	private StudentResponseDTO mapToResponse(Student student) {

		StudentResponseDTO dto = new StudentResponseDTO();

		dto.setId(student.getId());
		dto.setRollNumber(student.getRollNumber());
		dto.setFullName(student.getFullName());
		dto.setEmail(student.getEmail());
		dto.setMobile(student.getMobile());
		dto.setParentName(student.getParentName());
		dto.setParentMobile(student.getParentMobile());
		dto.setAddress(student.getAddress());
		dto.setSection(student.getSection());
		dto.setUserRole(student.getUserRole());
		dto.setAdminId(student.getAdmin().getAdminId());

		return dto;
	}

	// =========================
	// FIND BY ROLL NUMBER
	// =========================
	@Override
	public StudentResponseDTO getStudentByRollNumber(String rollNumber) {

		Student student = studentRepository.findByRollNumber(rollNumber)
				.orElseThrow(() -> new RuntimeException("Student not found"));

		return mapToResponse(student);
	}

	// =========================
	// FIND BY SECTION
	// =========================
	@Override
	public List<StudentResponseDTO> getStudentsBySection(Section section) {

		return studentRepository.findBySection(section).stream().map(this::mapToResponse).collect(Collectors.toList());
	}

	// =========================
	// FIND BY ROLL NUMBER + SECTION
	// =========================
	@Override
	public StudentResponseDTO getStudentByRollNumberAndSection(String rollNumber, Section section) {

		Student student = studentRepository.findByRollNumberAndSection(rollNumber, section)
				.orElseThrow(() -> new RuntimeException("Student not found"));

		return mapToResponse(student);
	}
	@Override
	public ResponseEntity<?> uploadProfilePicture(Long studentId, MultipartFile file, HttpServletRequest request) {

	    Student student = studentRepository.findById(studentId)
	            .orElseThrow(() -> new RuntimeException("Student not found"));

	    try {

	        String uploadDir = "uploads/students/";

	        File directory = new File(uploadDir);
	        if (!directory.exists()) {
	            directory.mkdirs();
	        }

	        String fileName = "student_" + studentId + "_" + file.getOriginalFilename();
	        String filePath = uploadDir + fileName;

	        File dest = new File(filePath);
	        file.transferTo(dest);

	        student.setProfilePicture("/" + filePath);

	        studentRepository.save(student);

	        return ResponseEntity.ok("Student profile picture uploaded successfully");

	    } catch (IOException e) {
	        return ResponseEntity.internalServerError().body("Upload failed");
	    }
	}
}