package com.tutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tutor.entity.HomeworkSubmission;

import java.util.List;

public interface HomeworkSubmissionRepository
        extends JpaRepository<HomeworkSubmission, Long> {

    List<HomeworkSubmission> findByStudentId(Long studentId);

    List<HomeworkSubmission> findByHomeworkId(Long homeworkId);
}
