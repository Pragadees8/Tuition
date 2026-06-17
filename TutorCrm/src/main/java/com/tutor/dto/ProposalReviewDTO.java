package com.tutor.dto;

import com.tutor.enums.Status;


public class ProposalReviewDTO {

    private Status status;  
    // APPROVED / REJECTED

    private String remarks;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
    
    
    
}
