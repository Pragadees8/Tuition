package com.tutor.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tutor.enums.PaymentStatus;

@JsonIgnoreProperties({
    "email",
    "mobile",
    "parentName",
    "parentMobile",
    "registeredByAdmin"
     })
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "fees")
public class Fees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
     @JsonIgnoreProperties({
        "feesList"})
    private Enrollment enrollment;

    private Double amount;

    private LocalDate paidDate;

    private String paymentMode;   // CASH / UPI / CARD
    

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

//    @ManyToOne 
//    @JsonIgnoreProperties({
//        "feesList",
//        "enrollments"    })
//    private Student student;

    @ManyToOne
    @JsonIgnoreProperties({
        "feesList",
        "students"
    })
    private Admin admin;
    
    private LocalDateTime paidAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
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

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public LocalDateTime getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(LocalDateTime paidAt) {
		this.paidAt = paidAt;
	}

    
}
