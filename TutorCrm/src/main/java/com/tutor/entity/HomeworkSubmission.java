package com.tutor.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tutor.enums.HomeworkStatus;
import com.tutor.enums.Role;

@Entity
@Table(name = "homework_submission")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeworkSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Homework reference
    @ManyToOne
    @JoinColumn(name = "homework_id")
    @JsonIgnoreProperties({"student", "teacher", "admin"})
    private Homework homework;

    // 👨‍🎓 Student who submitted
    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties({"teachers", "subjects"})
    private Student student;

    // 📄 Submission content / link
    private String submissionText;

    private String fileUrl; // optional (PDF/image link)

    private LocalDate submittedDate;

    @Enumerated(EnumType.STRING)
    private HomeworkStatus status; // SUBMITTED, LATE, EVALUATED
    @Enumerated(EnumType.STRING)
    private Role submittedby;
    
    private Integer marks; // optional

    private String teacherRemarks;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Homework getHomework() {
		return homework;
	}

	public void setHomework(Homework homework) {
		this.homework = homework;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getSubmissionText() {
		return submissionText;
	}

	public void setSubmissionText(String submissionText) {
		this.submissionText = submissionText;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public LocalDate getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(LocalDate submittedDate) {
		this.submittedDate = submittedDate;
	}

	public HomeworkStatus getStatus() {
		return status;
	}

	public void setStatus(HomeworkStatus status) {
		this.status = status;
	}

	public Role getSubmittedby() {
		return submittedby;
	}

	public void setSubmittedby(Role submittedby) {
		this.submittedby = submittedby;
	}

	public Integer getMarks() {
		return marks;
	}

	public void setMarks(Integer marks) {
		this.marks = marks;
	}

	public String getTeacherRemarks() {
		return teacherRemarks;
	}

	public void setTeacherRemarks(String teacherRemarks) {
		this.teacherRemarks = teacherRemarks;
	}

    // getters & setters
    
}
