package com.tutor.dto;

import lombok.Data;

@Data
public class SubjectRequestDTO {
    private String subjectName;
    private String description;
    private String level;
    private Long createdByAdminId;
    
    
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Long getCreatedByAdminId() {
		return createdByAdminId;
	}
	public void setCreatedByAdminId(Long createdByAdminId) {
		this.createdByAdminId = createdByAdminId;
	}
}