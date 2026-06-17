package com.tutor.dto;

import com.tutor.enums.EnrollmentStatus;

import lombok.Data;

public class EnrollmentResponseDTO {
	private Long id;
	private EnrollmentStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EnrollmentStatus getStatus() {
		return status;
	}

	public void setStatus(EnrollmentStatus status) {
		this.status = status;
	}

}