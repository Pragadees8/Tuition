package com.tutor.service;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tutor.entity.Admin;
import com.tutor.entity.Teacher;

import java.util.List;

@Service
public interface AdminService {

    ResponseEntity<?> createAdmin(Admin admin, Long superAdminId, HttpServletRequest http);

    List<Teacher> getEntireTeachers(HttpServletRequest request);

    List<ResponseEntity<?>> getEntireStudents(HttpServletRequest request);
    
    ResponseEntity<?> uploadProfilePicture(Long adminId, MultipartFile file);

}
