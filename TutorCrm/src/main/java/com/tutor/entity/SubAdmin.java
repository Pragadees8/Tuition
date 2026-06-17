package com.tutor.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import com.tutor.enums.Role;

@Entity
@Table(name = "sub_admins")
public class SubAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.SUB_ADMIN;

    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Column(name = "profile_picture")
    private String profilePicture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_superadmin_id", nullable = false)
    private SuperAdmin createdBy;

	public SubAdmin(Long id, String name, String email, String password, Role role, String phoneNumber,
			LocalDateTime createdAt, LocalDateTime updatedAt, SuperAdmin createdBy) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.phoneNumber = phoneNumber;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.createdBy = createdBy;
	}

	public SubAdmin() {
		super();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public SuperAdmin getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(SuperAdmin createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getProfilePicture() {
	    return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
	    this.profilePicture = profilePicture;
	}
    
  
}

