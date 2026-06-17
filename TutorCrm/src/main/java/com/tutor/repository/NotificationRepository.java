package com.tutor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutor.entity.Notification;
import com.tutor.enums.NotificationStatus;
import com.tutor.enums.Role;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	
	  // ✅ REQUIRED
    List<Notification> findByReceiverRoleOrderByCreatedAtDesc(Role receiverRole);

//    List<Notification> findByReceiverRoleOrderByCreatedAtDesc(Role role);

    Long countByReceiverRoleAndStatus(Role role, NotificationStatus status);
}
