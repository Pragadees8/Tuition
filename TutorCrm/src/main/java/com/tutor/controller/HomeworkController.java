package com.tutor.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tutor.entity.Homework;
import com.tutor.service.HomeworkService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/homework")
public class HomeworkController {

	@Autowired
	private HomeworkService service;

	@PostMapping("/create/{teacherId}/{subjectId}")
	public Homework create(@RequestBody Homework homework, @PathVariable Long teacherId,
						   @PathVariable Long subjectId, HttpServletRequest http) {

		return service.saveHomework(homework, teacherId, subjectId, http);
	}
	//////////////////////new
	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {

		try {
			System.out.println(" file : " + file);
			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			String uploadDir = "upload/";
			Path path = Paths.get(uploadDir + fileName);
			Files.write(path, file.getBytes());
			String fileUrl = "http://localhost:8082/upload/" + fileName;
			return ResponseEntity.ok(Map.of(
					"fileUrl", fileUrl
			));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
		}
	}

	@GetMapping
	public List<Homework> getAll() {
		return service.getAllHomework();
	}

	@GetMapping("/{id}")
	public Homework getById(@PathVariable Long id) {
		return service.getHomeworkById(id);
	}

	@PutMapping("/{id}/admin/{adminId}")
	public Homework update(
	        @PathVariable Long id,
	        @PathVariable Long adminId,
	        @RequestBody Homework homework) {

	    return service.updateHomework(id, homework, adminId);
	}


	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id) {
		service.deleteHomework(id);
		return "Homework deleted successfully";
	}
	
}
