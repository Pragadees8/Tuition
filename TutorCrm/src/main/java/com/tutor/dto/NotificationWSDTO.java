package com.tutor.dto;

import java.time.LocalDateTime;

import com.tutor.enums.NotificationType;
import com.tutor.enums.Role;

import lombok.Data;


public class NotificationWSDTO {

    private String title;

    private Long initiatorId;
    private String initiatorName;
    private Role initiatorRole;

    private NotificationType type;

    private String message;
    private LocalDateTime time;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
    
    
}
