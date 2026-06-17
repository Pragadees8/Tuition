package com.tutor.controller;

import com.tutor.entity.Teacher;

import com.tutor.service.TeacherService;
import com.tutor.service.UserService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

	@Autowired
	private TeacherService teacherService;

	@PostMapping("/create/{adminId}")
	public Teacher createTeacher(@Valid @RequestBody Teacher teacher, @PathVariable Long adminId) {

		return teacherService.saveTeacher(teacher, adminId);
	}

	@GetMapping("/all")
	public List<Teacher> getAllTeachers() {
		return teacherService.getAllTeachers();
	}

	@GetMapping("/{id}")
	public Teacher getTeacher(@PathVariable Long id) {
		return teacherService.getTeacherById(id);
	}

	@PutMapping("/{id}/{adminId}")
	public Teacher updateTeacher(@PathVariable Long id, @Valid @RequestBody Teacher teacher,
			@PathVariable Long adminId) {

		return teacherService.updateTeacher(id, teacher, adminId);
	}

	@DeleteMapping("/{id}")
	public String deleteTeacher(@PathVariable Long id) {
		teacherService.deleteTeacher(id);
		return "Teacher deleted successfully";
	}
	@PostMapping("/upload-profile/{teacherId}")
	public ResponseEntity<?> uploadProfile(
	        @PathVariable Long teacherId,
	        @RequestParam("file") MultipartFile file,
	        HttpServletRequest request
	) {
	    return teacherService.uploadProfilePicture(teacherId, file, request);
	}
}
