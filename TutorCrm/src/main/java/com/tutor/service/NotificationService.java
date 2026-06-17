package com.tutor.service;

import java.util.List;

import com.tutor.dto.NotificationDTO;
import com.tutor.enums.Role;

public interface NotificationService {

    void notifySuperAdminLogin(
            Long initiatorId,
            String initiatorName,
            Role initiatorRole
    );
    
    List<NotificationDTO> getSuperAdminNotifications();


	
}
