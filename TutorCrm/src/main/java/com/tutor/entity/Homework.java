package com.tutor.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.tutor.enums.HomeworkStatus;
import com.tutor.enums.Role;

@Entity
@Table(name = "homework")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Homework {
	
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "teacher_id", nullable = false)
	    @JsonIgnoreProperties({ "materials", "videos" })
	    private Teacher teacher;
	    //student

//	    @ManyToOne(fetch = FetchType.LAZY)
//	    @JoinColumn(name = "student_id", nullable = false)
//	    @JsonIgnoreProperties({ "students", "teachers", "subjects" })
//	    private Student student;

	    //sub
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "subject_id", nullable = false)
	    @JsonIgnoreProperties({ "materials", "videos" })
	    private Subject subject;

//	    // ================= ADMIN =================
//	    @ManyToOne(fetch = FetchType.LAZY)
//	    @JoinColumn(name = "admin_id", nullable = false)
//	    @JsonIgnoreProperties({ "students", "teachers", "subjects" })
//	    private Admin admin;

	    private String title;
	    private String description;

		private String documentUrl;

	    private LocalDate assignedDate;
	    private LocalDate dueDate;

		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;
		private String createdBy;
		private String updatedBy;

	    @Enumerated(EnumType.STRING)
	    private HomeworkStatus status;

		public Homework(){}

		public Homework(Long id, Teacher teacher, Subject subject, String title, String description, String documentUrl, LocalDate assignedDate, LocalDate dueDate, LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy, HomeworkStatus status) {
			this.id = id;
			this.teacher = teacher;
			this.subject = subject;
			this.title = title;
			this.description = description;
			this.documentUrl = documentUrl;
			this.assignedDate = assignedDate;
			this.dueDate = dueDate;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
			this.createdBy = createdBy;
			this.updatedBy = updatedBy;
			this.status = status;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Teacher getTeacher() {
			return teacher;
		}

		public void setTeacher(Teacher teacher) {
			this.teacher = teacher;
		}

		public Subject getSubject() {
			return subject;
		}

		public void setSubject(Subject subject) {
			this.subject = subject;
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

		public String getDocumentUrl() {
			return documentUrl;
		}

		public void setDocumentUrl(String documentUrl) {
			this.documentUrl = documentUrl;
		}

		public LocalDate getAssignedDate() {
			return assignedDate;
		}

		public void setAssignedDate(LocalDate assignedDate) {
			this.assignedDate = assignedDate;
		}

		public LocalDate getDueDate() {
			return dueDate;
		}

		public void setDueDate(LocalDate dueDate) {
			this.dueDate = dueDate;
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

		public String getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		public String getUpdatedBy() {
			return updatedBy;
		}

		public void setUpdatedBy(String updatedBy) {
			this.updatedBy = updatedBy;
		}

		public HomeworkStatus getStatus() {
			return status;
		}

		public void setStatus(HomeworkStatus status) {
			this.status = status;
		}
	}