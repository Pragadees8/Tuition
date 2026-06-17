package com.tutor.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tutor.dto.HomeworkSubmissionRequestDTO;
import com.tutor.dto.HomeworkSubmissionResponseDTO;
import com.tutor.entity.Homework;
import com.tutor.entity.HomeworkSubmission;
import com.tutor.entity.Student;
import com.tutor.enums.HomeworkStatus;
import com.tutor.enums.Role;
import com.tutor.repository.HomeworkRepository;
import com.tutor.repository.HomeworkSubmissionRepository;
import com.tutor.repository.StudentRepository;

@Service
public class HomeworkSubmissionServiceImpl implements HomeworkSubmissionService {

    @Autowired
    private HomeworkSubmissionRepository submissionRepository;

    @Autowired
    private HomeworkRepository homeworkRepository;

    @Autowired
    private StudentRepository studentRepository;

    // File upload directory - can be configured in application.properties
    @Value("${file.upload.dir:uploads/homework}")
    private String uploadDir;

    // ===============================
    // ✅ SUBMIT HOMEWORK (STUDENT) - Without File
    // ===============================
    @Override
    public HomeworkSubmissionResponseDTO submitHomework(HomeworkSubmissionRequestDTO dto) {

        Homework homework = homeworkRepository.findById(dto.getHomeworkId())
                .orElseThrow(() -> new RuntimeException("Homework not found"));

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        HomeworkSubmission submission = new HomeworkSubmission();
        submission.setHomework(homework);
        submission.setStudent(student);
        submission.setSubmissionText(dto.getSubmissionText());
        submission.setFileUrl(dto.getFileUrl());
        submission.setSubmittedDate(LocalDate.now());
        submission.setStatus(HomeworkStatus.SUBMITTED);
        submission.setSubmittedby(Role.STUDENT);

        HomeworkSubmission saved = submissionRepository.save(submission);

        return buildResponse(saved);
    }

    // ===============================
    // ✅ NEW: SUBMIT HOMEWORK WITH FILE UPLOAD
    // ===============================
    @Override
    public HomeworkSubmissionResponseDTO submitHomeworkWithFile(
            MultipartFile file, Long homeworkId, Long studentId) {

        Homework homework = homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new RuntimeException("Homework not found"));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Save the file and get the URL/path
        String fileUrl = saveFile(file);

        // Create submission
        HomeworkSubmission submission = new HomeworkSubmission();
        submission.setHomework(homework);
        submission.setStudent(student);
        submission.setFileUrl(fileUrl);
        submission.setSubmittedDate(LocalDate.now());
        submission.setStatus(HomeworkStatus.SUBMITTED);
        submission.setSubmittedby(Role.STUDENT);

        HomeworkSubmission saved = submissionRepository.save(submission);

        return buildResponse(saved);
    }

    // ===============================
    // 📁 FILE SAVING HELPER METHOD
    // ===============================
    private String saveFile(MultipartFile file) {
        try {
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename != null && originalFilename.contains(".")
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : "";
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            // Save file
            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Return FULL URL with backend domain
            // IMPORTANT: Include http://localhost:8099 so frontend can access it
            return "http://localhost:8099/uploads/homework/" + uniqueFilename;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + e.getMessage());
        }
    }

    // ===============================
    // ✅ GET SUBMISSIONS BY STUDENT
    // ===============================
    @Override
    public List<HomeworkSubmissionResponseDTO> getByStudent(Long studentId) {

        List<HomeworkSubmission> submissions =
                submissionRepository.findByStudentId(studentId);

        List<HomeworkSubmissionResponseDTO> responseList = new ArrayList<>();

        for (HomeworkSubmission s : submissions) {
            responseList.add(buildResponse(s));
        }

        return responseList;
    }

    // ===============================
    // ✅ GET SUBMISSIONS BY HOMEWORK
    // ===============================
    @Override
    public List<HomeworkSubmissionResponseDTO> getByHomework(Long homeworkId) {

        List<HomeworkSubmission> submissions =
                submissionRepository.findByHomeworkId(homeworkId);

        List<HomeworkSubmissionResponseDTO> responseList = new ArrayList<>();

        for (HomeworkSubmission s : submissions) {
            responseList.add(buildResponse(s));
        }

        return responseList;
    }

    // ===============================
    // ✅ EVALUATE HOMEWORK (TEACHER)
    // ===============================
    @Override
    public HomeworkSubmissionResponseDTO evaluateHomework(
            Long submissionId, Integer marks, String remarks) {

        HomeworkSubmission submission =
                submissionRepository.findById(submissionId)
                        .orElseThrow(() -> new RuntimeException("Submission not found"));

        submission.setMarks(marks);
        submission.setTeacherRemarks(remarks);
        submission.setStatus(HomeworkStatus.EVALUATED);

        HomeworkSubmission updated = submissionRepository.save(submission);

        return buildResponse(updated);
    }

    // ===============================
    // 🔁 COMMON RESPONSE BUILDER
    // ===============================
    private HomeworkSubmissionResponseDTO buildResponse(HomeworkSubmission s) {

        HomeworkSubmissionResponseDTO dto = new HomeworkSubmissionResponseDTO();
        dto.setId(s.getId());
        dto.setSubmissionText(s.getSubmissionText());
        dto.setFileUrl(s.getFileUrl());
        dto.setSubmittedDate(s.getSubmittedDate());
        dto.setStatus(s.getStatus());
        dto.setSubmittedby(s.getSubmittedby());
        dto.setMarks(s.getMarks());
        dto.setTeacherRemarks(s.getTeacherRemarks());

        return dto;
    }
}
