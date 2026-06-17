package com.tutor.dto;

import java.time.LocalDateTime;


import com.tutor.enums.Role;
import com.tutor.enums.Status;


public class ProposalResponseDTO {

    private Long id;

    private String title;

    private String description;

    private String proposalType;

    private Status status;

    private Role createdByRole;   // ✅ USE ENUM

    private Long createdById;

    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getProposalType() {
		return proposalType;
	}

	public void setProposalType(String proposalType) {
		this.proposalType = proposalType;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Role getCreatedByRole() {
		return createdByRole;
	}

	public void setCreatedByRole(Role createdByRole) {
		this.createdByRole = createdByRole;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
    
    
}
