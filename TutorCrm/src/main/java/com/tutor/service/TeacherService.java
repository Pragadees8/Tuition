package com.tutor.service;

import com.tutor.entity.Teacher;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface TeacherService {

    Teacher saveTeacher(Teacher teacher,Long adminId);

    List<Teacher> getAllTeachers();

    Teacher getTeacherById(Long id);

    Teacher updateTeacher(Long id, Teacher teacher,Long adminId);

    void deleteTeacher(Long id);
    
    ResponseEntity<?> uploadProfilePicture(Long teacherId, MultipartFile file, HttpServletRequest request);
}
