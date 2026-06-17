package com.tutor.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.tutor.enums.NotificationStatus;
import com.tutor.enums.NotificationType;
import com.tutor.enums.Role;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String message;

    //  Initiator (who performed the action)
    private Long initiatorId;
    private String initiatorName;

    @Enumerated(EnumType.STRING)
    private Role initiatorRole;

    //  Receiver
    @Enumerated(EnumType.STRING)
    private Role receiverRole; // SUPER_ADMIN

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status = NotificationStatus.UNREAD;

    private LocalDateTime createdAt;

    public Notification() {
        this.createdAt = LocalDateTime.now();
    }

	public Notification(Long id, String title, String message, Long initiatorId, String initiatorName,
			Role initiatorRole, Role receiverRole, NotificationType type, NotificationStatus status,
			LocalDateTime createdAt) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
		this.initiatorId = initiatorId;
		this.initiatorName = initiatorName;
		this.initiatorRole = initiatorRole;
		this.receiverRole = receiverRole;
		this.type = type;
		this.status = status;
		this.createdAt = createdAt;
	}

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

	public Role getInitiatorRole() {
		return initiatorRole;
	}

	public void setInitiatorRole(Role initiatorRole) {
		this.initiatorRole = initiatorRole;
	}

	public Role getReceiverRole() {
		return receiverRole;
	}

	public void setReceiverRole(Role receiverRole) {
		this.receiverRole = receiverRole;
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
