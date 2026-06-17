package com.tutor.service;

import java.util.List;

import com.tutor.dto.FeedbackRequestDTO;
import com.tutor.dto.FeedbackResponseDTO;

public interface FeedbackService {

    FeedbackResponseDTO saveFeedback(FeedbackRequestDTO dto);

    List<FeedbackResponseDTO> getAllFeedbacks();

    FeedbackResponseDTO getFeedbackById(Long id);

    FeedbackResponseDTO updateFeedback(Long id, FeedbackRequestDTO dto);

    void deleteFeedback(Long id);
}
