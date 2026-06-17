package com.tutor.controller;

import com.tutor.dto.FeedbackRequestDTO;
import com.tutor.dto.FeedbackResponseDTO;
import com.tutor.service.FeedbackService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // ✅ CREATE
    @PostMapping("/create")
    public FeedbackResponseDTO create(@RequestBody FeedbackRequestDTO dto) {
        return feedbackService.saveFeedback(dto);
    }

    // ✅ GET ALL
    @GetMapping("/all")
    public List<FeedbackResponseDTO> getAll() {
        return feedbackService.getAllFeedbacks();
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public FeedbackResponseDTO getById(@PathVariable Long id) {
        return feedbackService.getFeedbackById(id);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public FeedbackResponseDTO update(
            @PathVariable Long id,
            @RequestBody FeedbackRequestDTO dto) {
        return feedbackService.updateFeedback(id, dto);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return "Feedback deleted successfully";
    }
}
