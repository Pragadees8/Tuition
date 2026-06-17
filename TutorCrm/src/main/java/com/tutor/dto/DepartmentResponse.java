package com.tutor.dto;


public class DepartmentResponse {
    private Long id;
    private String departmentName;
    private String description;
    private boolean approved;
    private String status;
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	private String location;
    private String contactNumber;
    private Long createdBySubAdminId;
    private Long approvedByAdminId;
    private Long managementAdminId;
	
	public Long getCreatedBySubAdminId() {
		return createdBySubAdminId;
	}
	
	public void setCreatedBySubAdminId(Long createdBySubAdminId) {
		this.createdBySubAdminId = createdBySubAdminId;
	}
	
	public Long getApprovedByAdminId() {
		return approvedByAdminId;
	}
	
	public void setApprovedByAdminId(Long approvedByAdminId) {
		this.approvedByAdminId = approvedByAdminId;
	}
	
	public Long getManagementAdminId() {
		return managementAdminId;
	}
	
	public void setManagementAdminId(Long managementAdminId) {
		this.managementAdminId = managementAdminId;
	}

	
}
