package com.tutor.dto;

import com.tutor.enums.Role;

public class ProposalRequestDTO {

    private String title;

    private String description;

    private String proposalType;
    // DISCOUNT / STAFF / FEATURE / PLAN
    
    // Optional: only used if no token
    private Long userId;
    private Role role; // ADMIN or SUB_ADMIN

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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
    
}
