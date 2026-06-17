package com.tutor.controller;

import com.tutor.entity.Subject;
import com.tutor.service.SubjectService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

	@Autowired
    private SubjectService subjectService;


    @PostMapping("/create")
    public Subject createSubject(@RequestBody Subject subject, HttpServletRequest http) {
        return subjectService.createSubject(subject, http);
    }

    @GetMapping("/all")
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    public Subject getSubject(@PathVariable Long id) {
        return subjectService.getSubjectById(id);
    }

    @PutMapping("/update/{id}/{adminId}")
    public Subject updateSubject(@PathVariable Long id, @RequestBody Subject subject,@PathVariable Long adminId) {
        return subjectService.updateSubject(id, subject, adminId);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return "Subject deleted successfully";
    }
}
