package com.tutor.dto;

import com.tutor.enums.Role;

import lombok.Data;

@Data
public class TeacherResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String qualification;
    private Integer experience;
    private String primarySubject;
    private String status;
    public Role userRole;
    
	public Role getUserRole() {
		return userRole;
	}
	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public Integer getExperience() {
		return experience;
	}
	public void setExperience(Integer experience) {
		this.experience = experience;
	}
	public String getPrimarySubject() {
		return primarySubject;
	}
	public void setPrimarySubject(String primarySubject) {
		this.primarySubject = primarySubject;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
    
}