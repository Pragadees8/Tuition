package com.tutor.dto;

import java.time.LocalDate;

import com.tutor.enums.HomeworkStatus;
import com.tutor.enums.Role;

public class HomeworkSubmissionResponseDTO {

    private Long id;
    private String submissionText;
    private String fileUrl;
    private LocalDate submittedDate;
    private HomeworkStatus status;

    private Integer marks;
    private String teacherRemarks;
    private Role submittedby;

    public Role getSubmittedby() {
        return submittedby;
    }

    public void setSubmittedby(Role submittedby) {
        this.submittedby = submittedby;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
