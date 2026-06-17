package com.tutor.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tutor.enums.EnrollmentStatus;
import com.tutor.enums.Status;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "enrollments")
public class Enrollment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 🔴 Stop Student → Enrollments → Student loop
	@ManyToOne
	@JsonIgnoreProperties({ "enrollments", "feesList" })
	private Student student;

	// 🔴 Stop Teacher → Enrollments → Teacher loop
	@ManyToOne
	@JsonIgnoreProperties({ "enrollments", "materials", "videos" })
	private Teacher teacher;

	// ✔ Subject is already safe
	@ManyToOne
	@JsonIgnoreProperties({ "materials", "videos" })
	private Subject subject;

	private LocalDateTime enrolledAt = LocalDateTime.now();

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EnrollmentStatus status;

	// 🔴 Break Enrollment → Fees → Enrollment
	@OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Fees> feesList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
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

	public LocalDateTime getEnrolledAt() {
		return enrolledAt;
	}

	public void setEnrolledAt(LocalDateTime enrolledAt) {
		this.enrolledAt = enrolledAt;
	}

	public EnrollmentStatus getStatus() {
		return status;
	}

	public void setStatus(EnrollmentStatus status) {
		this.status = status;
	}

	public List<Fees> getFeesList() {
		return feesList;
	}

	public void setFeesList(List<Fees> feesList) {
		this.feesList = feesList;
	}

}
