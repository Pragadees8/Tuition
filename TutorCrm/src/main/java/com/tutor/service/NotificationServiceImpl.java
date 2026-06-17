package com.tutor.service;

import com.tutor.dto.NotificationDTO;
import com.tutor.entity.Notification;
import com.tutor.enums.NotificationStatus; // ✅ ADDED
import com.tutor.enums.NotificationType;
import com.tutor.enums.Role;
import com.tutor.repository.NotificationRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void notifySuperAdminLogin(Long initiatorId,
                                      String initiatorName,
                                      Role initiatorRole) {

        String title = "Login Activity";
        String message = initiatorRole + " " + initiatorName + " logged in";

        // save to DB
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setInitiatorId(initiatorId);
        notification.setInitiatorName(initiatorName);
        notification.setInitiatorRole(initiatorRole);
        notification.setReceiverRole(Role.SUPER_ADMIN);
        notification.setType(NotificationType.LOGIN);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        // send real-time
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());                    // ✅ ADDED
        dto.setTitle(title);
        dto.setMessage(message);
        dto.setInitiatorId(initiatorId);
        dto.setInitiatorName(initiatorName);
        dto.setInitiatorRole(initiatorRole);
        dto.setReceiverRole(Role.SUPER_ADMIN);
        dto.setType(NotificationType.LOGIN);
        dto.setStatus(NotificationStatus.UNREAD);           // ✅ ADDED
        dto.setCreatedAt(notification.getCreatedAt());      // ✅ ADDED

        messagingTemplate.convertAndSend(
                "/topic/superadmin/notifications",
                dto
        );
    }

    @Override
    public List<NotificationDTO> getSuperAdminNotifications() {

        return notificationRepository
                .findByReceiverRoleOrderByCreatedAtDesc(Role.SUPER_ADMIN)
                .stream()
                .map(notification -> {
                    NotificationDTO dto = new NotificationDTO();
                    dto.setId(notification.getId());
                    dto.setTitle(notification.getTitle());
                    dto.setMessage(notification.getMessage());
                    dto.setInitiatorId(notification.getInitiatorId());
                    dto.setInitiatorName(notification.getInitiatorName());
                    dto.setInitiatorRole(notification.getInitiatorRole());
                    dto.setReceiverRole(notification.getReceiverRole());
                    dto.setType(notification.getType());
                    dto.setStatus(notification.getStatus()); // already correct
                    dto.setCreatedAt(notification.getCreatedAt());
                    return dto;
                })
                .toList();
    }
}
