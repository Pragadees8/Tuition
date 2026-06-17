package com.tutor.service;

import java.util.List;

import com.tutor.dto.AttendanceRequestDTO;
import com.tutor.dto.AttendanceResponseDTO;

public interface AttendanceService {

    AttendanceResponseDTO saveAttendance(AttendanceRequestDTO dto);

    List<AttendanceResponseDTO> getAllAttendances();

    AttendanceResponseDTO getAttendanceById(Long id);

    AttendanceResponseDTO updateAttendance(Long id, AttendanceRequestDTO dto);

    void deleteAttendance(Long id);
}