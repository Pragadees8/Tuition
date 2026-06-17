package com.tutor.controller;

import com.tutor.dto.HomeworkSubmissionRequestDTO;
import com.tutor.dto.HomeworkSubmissionResponseDTO;
import com.tutor.service.HomeworkSubmissionService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/homework-submission")
public class HomeworkSubmissionController {

    private final HomeworkSubmissionService service;

    public HomeworkSubmissionController(HomeworkSubmissionService service) {
        this.service = service;
    }

    // ✅ SUBMIT HOMEWORK
    @PostMapping("/submit")
    public HomeworkSubmissionResponseDTO submitHomework(
            @RequestBody HomeworkSubmissionRequestDTO dto) {
        return service.submitHomework(dto);
    }

    // ✅ GET SUBMISSIONS BY STUDENT
    @GetMapping("/student/{studentId}")
    public List<HomeworkSubmissionResponseDTO> getByStudent(
            @PathVariable Long studentId) {
        return service.getByStudent(studentId);
    }

    // ✅ GET SUBMISSIONS BY HOMEWORK
    @GetMapping("/homework/{homeworkId}")
    public List<HomeworkSubmissionResponseDTO> getByHomework(
            @PathVariable Long homeworkId) {
        return service.getByHomework(homeworkId);
    }

    // ✅ EVALUATE HOMEWORK (Teacher)
    @PutMapping("/evaluate/{id}")
    public HomeworkSubmissionResponseDTO evaluateHomework(
            @PathVariable Long id,
            @RequestParam("marks") Integer marks,
            @RequestParam("remarks") String remarks) {
        return service.evaluateHomework(id, marks, remarks);
    }
}
