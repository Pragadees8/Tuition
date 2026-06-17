package com.tutor.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.tutor.enums.ProposalType;
import com.tutor.enums.Status;

@Entity
@Table(name = "proposals")
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private ProposalType proposalType; // DISCOUNT / STAFF / FEATURE / BRANCH / PLAN

    @Enumerated(EnumType.STRING)
    private Status status;
    // PENDING / APPROVED / REJECTED

    // RELATIONS

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "sub_admin_id")
    private SubAdmin subAdmin;

    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    private SuperAdmin reviewedBy;

    // AUDIT 

    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

	public Proposal(Long id, String title, String description, ProposalType proposalType, Status status, Admin admin,
			SubAdmin subAdmin, SuperAdmin reviewedBy, String remarks, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.proposalType = proposalType;
		this.status = status;
		this.admin = admin;
		this.subAdmin = subAdmin;
		this.reviewedBy = reviewedBy;
		this.remarks = remarks;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Proposal() {
		super();
		// TODO Auto-generated constructor stub
	}

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

	public ProposalType getProposalType() {
		return proposalType;
	}

	public void setProposalType(ProposalType proposalType) {
		this.proposalType = proposalType;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public SubAdmin getSubAdmin() {
		return subAdmin;
	}

	public void setSubAdmin(SubAdmin subAdmin) {
		this.subAdmin = subAdmin;
	}

	public SuperAdmin getReviewedBy() {
		return reviewedBy;
	}

	public void setReviewedBy(SuperAdmin reviewedBy) {
		this.reviewedBy = reviewedBy;
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
