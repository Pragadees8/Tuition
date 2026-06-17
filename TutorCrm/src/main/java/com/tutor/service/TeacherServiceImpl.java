package com.tutor.service;

import com.tutor.entity.Admin;
import com.tutor.entity.Teacher;
import com.tutor.entity.User;
import com.tutor.enums.Role;
import com.tutor.enums.Status;
import com.tutor.repository.AdminRepository;
import com.tutor.repository.TeacherRepository;
import com.tutor.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import java.io.File;
import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder; 

    @Override
    @Transactional
    public Teacher saveTeacher(Teacher teacher, Long adminId) {

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // Check email once
        if (userRepository.existsByEmail(teacher.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create USER
        User user = new User();
        user.setName(teacher.getName());
        user.setEmail(teacher.getEmail());
        user.setPassword(passwordEncoder.encode(teacher.getPassword()));
        user.setRole(Role.TEACHER);
        user.setCreatedAt(LocalDateTime.now());
        user.setFirstLogin(true);

        userRepository.save(user);

        // Create TEACHER
        Teacher newTeacher = new Teacher();
        newTeacher.setName(teacher.getName());
        newTeacher.setEmail(teacher.getEmail());
        newTeacher.setMobile(teacher.getMobile());
        newTeacher.setQualification(teacher.getQualification());
        newTeacher.setExperience(teacher.getExperience());
        newTeacher.setPrimarySubject(teacher.getPrimarySubject());
        newTeacher.setUserRole(Role.TEACHER);
        newTeacher.setCreatedByAdmin(admin);

        if (teacher.getStatus() == null) {
            newTeacher.setStatus(Status.INACTIVE);
        } else {
            newTeacher.setStatus(teacher.getStatus());
        }

        return teacherRepository.save(newTeacher);
    }
    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
    }

    @Override
    public Teacher updateTeacher(Long id, Teacher teacher, Long adminId) {

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        Teacher existing = getTeacherById(id);
        existing.setName(teacher.getName());
        existing.setEmail(teacher.getEmail());
        existing.setMobile(teacher.getMobile());
        existing.setQualification(teacher.getQualification());
        existing.setExperience(teacher.getExperience());
        existing.setUserRole(Role.TEACHER);
        existing.setPrimarySubject(teacher.getPrimarySubject());
        existing.setStatus(teacher.getStatus());
        existing.setCreatedByAdmin(admin);

        return teacherRepository.save(existing);
    }

    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }
   
	@Override
	public ResponseEntity<?> uploadProfilePicture(Long teacherId, MultipartFile file, HttpServletRequest request) {
		Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        try {

            String uploadDir = "uploads/teachers/";

            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = "teacher_" + teacherId + "_" + file.getOriginalFilename();
            String filePath = uploadDir + fileName;

            File dest = new File(filePath);
            file.transferTo(dest);

            teacher.setProfilePicture("/" + filePath);

            teacherRepository.save(teacher);

            return ResponseEntity.ok("Teacher profile picture uploaded successfully");

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Upload failed");
        }
	}
}
