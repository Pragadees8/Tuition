package com.tutor.service;

import com.tutor.dto.FeedbackRequestDTO;
import com.tutor.dto.FeedbackResponseDTO;
import com.tutor.entity.Feedback;
import com.tutor.entity.Student;
import com.tutor.entity.Teacher;
import com.tutor.repository.FeedbackRepository;
import com.tutor.repository.StudentRepository;
import com.tutor.repository.TeacherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    // ================= CREATE =================
    @Override
    public FeedbackResponseDTO saveFeedback(FeedbackRequestDTO dto) {

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Feedback feedback = new Feedback();
        feedback.setStudent(student);
        feedback.setTeacher(teacher);
        feedback.setComments(dto.getComments());
        feedback.setRating(dto.getRating());
        feedback.setCreatedAt(LocalDateTime.now());
        // ❌ DO NOT SET updateAt FOR CREATE

        Feedback saved = feedbackRepository.save(feedback);

        // CREATE RESPONSE (NO updateAt)
        FeedbackResponseDTO res = new FeedbackResponseDTO();
        res.setId(saved.getId());
        res.setComments(saved.getComments());
        res.setRating(saved.getRating());
        res.setCreatedAt(saved.getCreatedAt());

        return res;
    }

    // ================= GET ALL =================
    @Override
    public List<FeedbackResponseDTO> getAllFeedbacks() {

        List<Feedback> list = feedbackRepository.findAll();
        List<FeedbackResponseDTO> responseList = new ArrayList<>();

        for (Feedback feedback : list) {
            responseList.add(mapToResponse(feedback));
        }

        return responseList;
    }

    // ================= GET BY ID =================
    @Override
    public FeedbackResponseDTO getFeedbackById(Long id) {

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        return mapToResponse(feedback);
    }

    // ================= UPDATE =================
    @Override
    public FeedbackResponseDTO updateFeedback(Long id, FeedbackRequestDTO dto) {

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        feedback.setStudent(student);
        feedback.setTeacher(teacher);
        feedback.setComments(dto.getComments());
        feedback.setRating(dto.getRating());
        feedback.setUpdateAt(LocalDateTime.now()); // ✅ ONLY FOR UPDATE

        Feedback updated = feedbackRepository.save(feedback);

        return mapToResponse(updated);
    }

    // ================= DELETE =================
    @Override
    public void deleteFeedback(Long id) {

        if (!feedbackRepository.existsById(id)) {
            throw new RuntimeException("Feedback not found");
        }

        feedbackRepository.deleteById(id);
    }

    // ================= COMMON MAPPER =================
    private FeedbackResponseDTO mapToResponse(Feedback feedback) {

        FeedbackResponseDTO res = new FeedbackResponseDTO();
        res.setId(feedback.getId());
        res.setComments(feedback.getComments());
        res.setRating(feedback.getRating());
        res.setCreatedAt(feedback.getCreatedAt());
        res.setUpdateAt(feedback.getUpdateAt()); // used for GET / UPDATE

        return res;
    }
}
