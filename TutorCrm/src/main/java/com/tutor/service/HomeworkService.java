package com.tutor.service;

import com.tutor.entity.Homework;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface HomeworkService {

	Homework saveHomework(
            Homework homework,
            Long teacherId,
            Long subjectId,
            HttpServletRequest http
    );
    List<Homework> getAllHomework();

    Homework getHomeworkById(Long id);

    Homework updateHomework(Long homeworkId, Homework homework, Long adminId);

    void deleteHomework(Long id);
	Homework getHomeworkBystudentId(Long studentId);
}
