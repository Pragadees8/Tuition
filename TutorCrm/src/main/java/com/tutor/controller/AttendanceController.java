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

import com.tutor.dto.AttendanceRequestDTO;
import com.tutor.dto.AttendanceResponseDTO;
import com.tutor.service.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService service;

    // ✅ CREATE
    @PostMapping("/create")
    public AttendanceResponseDTO create(@RequestBody AttendanceRequestDTO dto) {
        return service.saveAttendance(dto);
    }

    // ✅ GET ALL
    @GetMapping("/all")
    public List<AttendanceResponseDTO> getAll() {
        return service.getAllAttendances();
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public AttendanceResponseDTO getById(@PathVariable Long id) {
        return service.getAttendanceById(id);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public AttendanceResponseDTO update(
            @PathVariable Long id,
            @RequestBody AttendanceRequestDTO dto) {
        return service.updateAttendance(id, dto);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteAttendance(id);
        return "Attendance deleted successfully";
    }
}