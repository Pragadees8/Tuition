package com.tutor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutor.dto.EnrollmentRequestDTO;
import com.tutor.dto.EnrollmentResponseDTO;
import com.tutor.service.EnrollmentService;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    // ✅ CREATE ENROLLMENT
    @PostMapping("/create")
    public EnrollmentResponseDTO createEnrollment(
            @RequestBody EnrollmentRequestDTO dto) {
        return enrollmentService.saveEnrollment(dto);
    }

    // ✅ GET ALL ENROLLMENTS
    @GetMapping("/all")
    public List<EnrollmentResponseDTO> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    // ✅ GET ENROLLMENT BY ID
    @GetMapping("/{id}")
    public EnrollmentResponseDTO getEnrollmentById(
            @PathVariable Long id) {
        return enrollmentService.getEnrollmentById(id);
    }

    // ✅ UPDATE ENROLLMENT
    @PutMapping("/{id}")
    public EnrollmentResponseDTO updateEnrollment(
            @PathVariable Long id,
            @RequestBody EnrollmentRequestDTO dto) {
        return enrollmentService.updateEnrollment(id, dto);
    }

    // ✅ DELETE ENROLLMENT
    @DeleteMapping("/{id}")
    public String deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return "Enrollment deleted successfully";
    }
}