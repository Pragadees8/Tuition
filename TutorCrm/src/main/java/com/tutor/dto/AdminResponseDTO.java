package com.tutor.dto;

import com.tutor.enums.Role;

import lombok.Data;
public class AdminResponseDTO {
    public Long adminId;
    public String fullName;
    public String email;
    public String phone;
    public Role userRole;
    public Long createdBySuperAdminId;
	public Long getAdminId() {
		return adminId;
	}
	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Role getUserRole() {
		return userRole;
	}
	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}
	public Long getCreatedBySuperAdminId() {
		return createdBySuperAdminId;
	}
	public void setCreatedBySuperAdminId(Long createdBySuperAdminId) {
		this.createdBySuperAdminId = createdBySuperAdminId;
	}
    
}
