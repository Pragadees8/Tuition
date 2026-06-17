package com.tutor.service;

import java.util.List;

import com.tutor.dto.EnrollmentRequestDTO;
import com.tutor.dto.EnrollmentResponseDTO;

public interface EnrollmentService {

    EnrollmentResponseDTO saveEnrollment(EnrollmentRequestDTO dto);

    List<EnrollmentResponseDTO> getAllEnrollments();

    EnrollmentResponseDTO getEnrollmentById(Long id);

    EnrollmentResponseDTO updateEnrollment(Long id, EnrollmentRequestDTO dto);

    void deleteEnrollment(Long id);
}
