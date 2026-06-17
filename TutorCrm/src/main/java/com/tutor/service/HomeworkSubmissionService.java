package com.tutor.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tutor.dto.HomeworkSubmissionRequestDTO;
import com.tutor.dto.HomeworkSubmissionResponseDTO;

public interface HomeworkSubmissionService {

	HomeworkSubmissionResponseDTO submitHomework(HomeworkSubmissionRequestDTO dto);

	List<HomeworkSubmissionResponseDTO> getByStudent(Long studentId);

	List<HomeworkSubmissionResponseDTO> getByHomework(Long homeworkId);

	HomeworkSubmissionResponseDTO evaluateHomework(Long submissionId, Integer marks, String remarks);
	
	HomeworkSubmissionResponseDTO submitHomeworkWithFile(MultipartFile file, Long homeworkId, Long studentId);
}
