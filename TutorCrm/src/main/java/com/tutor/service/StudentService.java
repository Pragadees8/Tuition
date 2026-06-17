package com.tutor.service;

import com.tutor.dto.StudentRequestDTO;
import com.tutor.dto.StudentResponseDTO;
import com.tutor.enums.Section;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface StudentService {

    StudentResponseDTO createStudent(StudentRequestDTO dto, Long adminId);

    List<StudentResponseDTO> getStudentsByAdmin(Long adminId);

    StudentResponseDTO getStudentByAdmin(Long adminId, Long studentId);

    StudentResponseDTO updateStudentByAdmin(Long adminId, Long studentId, StudentRequestDTO dto);

    void deleteStudentByAdmin(Long adminId, Long studentId);
    
    StudentResponseDTO getStudentByRollNumber(String rollNumber);

    List<StudentResponseDTO> getStudentsBySection(Section section);

    StudentResponseDTO getStudentByRollNumberAndSection(String rollNumber, Section section);
    
    ResponseEntity<?> uploadProfilePicture(Long studentId, MultipartFile file, HttpServletRequest request);
}