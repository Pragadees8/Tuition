package com.tutor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutor.dto.EnrollmentRequestDTO;
import com.tutor.dto.EnrollmentResponseDTO;
import com.tutor.entity.Enrollment;
import com.tutor.entity.Student;
import com.tutor.entity.Subject;
import com.tutor.entity.Teacher;
import com.tutor.enums.EnrollmentStatus;
import com.tutor.repository.EnrollmentRepository;
import com.tutor.repository.StudentRepository;
import com.tutor.repository.SubjectRepository;
import com.tutor.repository.TeacherRepository;
import com.tutor.service.EnrollmentService;

@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {
	@Autowired
    private  EnrollmentRepository enrollmentRepository;
	@Autowired
    private  StudentRepository studentRepository;
	@Autowired
    private  TeacherRepository teacherRepository;
	@Autowired
    private  SubjectRepository subjectRepository;


    // ✅ CREATE ENROLLMENT
    @Override
    public EnrollmentResponseDTO saveEnrollment(EnrollmentRequestDTO dto) {

        validateDto(dto);

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setTeacher(teacher);
        enrollment.setSubject(subject);
        enrollment.setStatus(
                dto.getStatus() != null ? dto.getStatus() : EnrollmentStatus.ACTIVE
        );

        Enrollment saved = enrollmentRepository.save(enrollment);

        return mapToResponse(saved);
    }

    // ✅ GET ALL
    @Override
    public List<EnrollmentResponseDTO> getAllEnrollments() {
        return enrollmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ GET BY ID
    @Override
    public EnrollmentResponseDTO getEnrollmentById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        return mapToResponse(enrollment);
    }

    // ✅ UPDATE
    @Override
    public EnrollmentResponseDTO updateEnrollment(Long id, EnrollmentRequestDTO dto) {

        validateDto(dto);

        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        enrollment.setStudent(student);
        enrollment.setTeacher(teacher);
        enrollment.setSubject(subject);
        enrollment.setStatus(
                dto.getStatus() != null ? dto.getStatus() : enrollment.getStatus()
        );

        return mapToResponse(enrollmentRepository.save(enrollment));
    }

    // ✅ DELETE
    @Override
    public void deleteEnrollment(Long id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new RuntimeException("Enrollment not found");
        }
        enrollmentRepository.deleteById(id);
    }

    // 🔹 VALIDATION
    private void validateDto(EnrollmentRequestDTO dto) {
        if (dto.getStudentId() == null)
            throw new RuntimeException("studentId must not be null");

        if (dto.getTeacherId() == null)
            throw new RuntimeException("teacherId must not be null");

        if (dto.getSubjectId() == null)
            throw new RuntimeException("subjectId must not be null");
    }

    // 🔹 MAPPER
    private EnrollmentResponseDTO mapToResponse(Enrollment enrollment) {
        EnrollmentResponseDTO response = new EnrollmentResponseDTO();
        response.setId(enrollment.getId());
        response.setStatus(enrollment.getStatus());
        return response;
    }
}
