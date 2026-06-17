package com.tutor.dto;

import java.time.LocalDate;

import com.tutor.enums.AttendanceStatus;

public class AttendanceRequestDTO {

    private LocalDate date;
    private AttendanceStatus status;
    private Long studentId;
    private Long teacherId;

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public AttendanceStatus getStatus() {
        return status;
    }
    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }
    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public Long getTeacherId() {
        return teacherId;
    }
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
