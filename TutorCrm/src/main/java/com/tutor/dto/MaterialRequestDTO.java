package com.tutor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import com.tutor.enums.MaterialStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class MaterialRequestDTO {

	@NotNull(message = "Teacher ID is required")
	private Long teacherId;

	@NotNull(message = "Subject ID is required")
	private Long subjectId;

	@NotNull(message = "Admin ID is required")
	private Long adminId;

	@NotBlank(message = "Title is required")
	private String title;

	private String description;

	@NotBlank(message = "File URL is required")
	private String fileUrl;

	private MaterialStatus status;

	// Getters & Setters
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

	public MaterialStatus getStatus() {
		return status;
	}

	public void setStatus(MaterialStatus status) {
		this.status = status;
	}
}
