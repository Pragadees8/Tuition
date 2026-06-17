package com.tutor.dto;

import java.time.LocalDate;

import com.tutor.enums.HomeworkStatus;
import com.tutor.enums.Role;

public class HomeworkSubmissionRequestDTO {

    private Long homeworkId;
    private Long studentId;

    private String submissionText;
    private String fileUrl;

    private LocalDate submittedDate;
    private HomeworkStatus status;
    private Role submittedby;

    public Long getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(Long homeworkId) {
        this.homeworkId = homeworkId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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
}
