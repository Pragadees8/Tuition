package com.tutor.service;

import com.tutor.entity.Subject;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface SubjectService {
	
	 Subject createSubject(Subject subject, HttpServletRequest http);

	    List<Subject> getAllSubjects();

	    Subject getSubjectById(Long id);

	    Subject updateSubject(Long id, Subject subject,Long adminId);

	    void deleteSubject(Long id);
}

//    Subject saveSubject(Subject subject, Long adminId);
//
//    List<Subject> getAllSubjects();
//
//    Subject getSubjectById(Long id);
//
//    Subject updateSubject(Long id, Subject subject,Long adminId);
//
//    void deleteSubject(Long id);
//}
