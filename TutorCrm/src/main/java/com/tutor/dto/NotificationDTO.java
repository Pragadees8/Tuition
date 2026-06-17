package com.tutor.dto;

import java.time.LocalDateTime;

import com.tutor.enums.NotificationStatus;
import com.tutor.enums.NotificationType;
import com.tutor.enums.Role;

public class NotificationDTO {

    private Long id;

    private String title;
    private String message;

    private Long initiatorId;
    private String initiatorName;
    private Role initiatorRole;

    private Role receiverRole;
    private NotificationType type;
    private NotificationStatus status;

    private LocalDateTime createdAt;

    
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getInitiatorId() {
		return initiatorId;
	}

	public void setInitiatorId(Long initiatorId) {
		this.initiatorId = initiatorId;
	}

	public String getInitiatorName() {
		return initiatorName;
	}

	public void setInitiatorName(String initiatorName) {
		this.initiatorName = initiatorName;
	}
	
	

	public Role getReceiverRole() {
		return receiverRole;
	}

	public void setReceiverRole(Role receiverRole) {
		this.receiverRole = receiverRole;
	}

	public Role getInitiatorRole() {
		return initiatorRole;
	}

	public void setInitiatorRole(Role initiatorRole) {
		this.initiatorRole = initiatorRole;
	}

	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	public NotificationStatus getStatus() {
		return status;
	}

	public void setStatus(NotificationStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

    
}
