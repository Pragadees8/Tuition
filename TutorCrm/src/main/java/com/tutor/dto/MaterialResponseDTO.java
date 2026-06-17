package com.tutor.dto;

import java.time.LocalDate;
import lombok.Data;

import java.time.LocalDate;
import com.tutor.enums.MaterialStatus;

@Data
public class MaterialResponseDTO {

	private Long id;
	private Long teacherId;
	private Long subjectId;
	private Long adminId;
	private String title;
	private String description;
	private String fileUrl;
	private LocalDate uploadedAt;
	private MaterialStatus status;

	// Getters & Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public LocalDate getUploadedAt() {
		return uploadedAt;
	}

	public void setUploadedAt(LocalDate uploadedAt) {
		this.uploadedAt = uploadedAt;
	}

	public MaterialStatus getStatus() {
		return status;
	}

	public void setStatus(MaterialStatus status) {
		this.status = status;
	}
}
