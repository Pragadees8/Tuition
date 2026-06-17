package com.tutor.controller;

import com.tutor.dto.StudentRequestDTO;
import com.tutor.dto.StudentResponseDTO;
import com.tutor.enums.Section;
import com.tutor.service.StudentService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;


    // =========================
    // CREATE STUDENT
    // =========================
    @PostMapping("/create/{adminId}")
    public StudentResponseDTO createStudent(
            @RequestBody StudentRequestDTO dto,
            @PathVariable Long adminId) {

        return studentService.createStudent(dto, adminId);
    }


    // =========================
    // GET ALL STUDENTS BY ADMIN
    // =========================
    @GetMapping("/admin/{adminId}")
    public List<StudentResponseDTO> getStudentsByAdmin(@PathVariable Long adminId) {

        return studentService.getStudentsByAdmin(adminId);
    }


    // =========================
    // GET SINGLE STUDENT BY ADMIN
    // =========================
    @GetMapping("/admin/{adminId}/{studentId}")
    public StudentResponseDTO getStudentByAdmin(
            @PathVariable Long adminId,
            @PathVariable Long studentId) {

        return studentService.getStudentByAdmin(adminId, studentId);
    }


    // =========================
    // UPDATE STUDENT
    // =========================
    @PutMapping("/admin/{adminId}/{studentId}")
    public StudentResponseDTO updateStudent(
            @PathVariable Long adminId,
            @PathVariable Long studentId,
            @RequestBody StudentRequestDTO dto) {

        return studentService.updateStudentByAdmin(adminId, studentId, dto);
    }


    // =========================
    // DELETE STUDENT
    // =========================
    @DeleteMapping("/admin/{adminId}/{studentId}")
    public String deleteStudent(
            @PathVariable Long adminId,
            @PathVariable Long studentId) {

        studentService.deleteStudentByAdmin(adminId, studentId);

        return "Student deleted successfully";
    }


    // =========================
    // FIND STUDENT BY ROLL NUMBER
    // =========================
    @GetMapping("/roll/{rollNumber}")
    public StudentResponseDTO getStudentByRollNumber(@PathVariable String rollNumber) {

        return studentService.getStudentByRollNumber(rollNumber);
    }


    // =========================
    // FIND STUDENTS BY SECTION
    // =========================
    @GetMapping("/section/{section}")
    public List<StudentResponseDTO> getStudentsBySection(@PathVariable Section section) {

        return studentService.getStudentsBySection(section);
    }


    // =========================
    // FIND BY ROLL NUMBER + SECTION
    // =========================
    @GetMapping("/roll/{rollNumber}/section/{section}")
    public StudentResponseDTO getStudentByRollAndSection(
            @PathVariable String rollNumber,
            @PathVariable Section section) {

        return studentService.getStudentByRollNumberAndSection(rollNumber, section);
    }
    @PostMapping("/upload-profile/{studentId}")
    public ResponseEntity<?> uploadProfile(
            @PathVariable Long studentId,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) {
        return studentService.uploadProfilePicture(studentId, file, request);
    }
}