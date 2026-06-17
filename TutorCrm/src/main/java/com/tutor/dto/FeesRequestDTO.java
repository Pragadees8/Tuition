package com.tutor.dto;

import java.time.LocalDate;

public class FeesRequestDTO {

    private Long enrollmentId;
    private Long adminId;

    private Double amount;
    private LocalDate paidDate;
    private String paymentMode;     // CASH / UPI / CARD
    private String paymentStatus;   // PAID / PENDING

    public Long getEnrollmentId() {
        return enrollmentId;
    }
    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }
    public Long getAdminId() {
        return adminId;
    }
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public LocalDate getPaidDate() {
        return paidDate;
    }
    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }
    public String getPaymentMode() {
        return paymentMode;
    }
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
