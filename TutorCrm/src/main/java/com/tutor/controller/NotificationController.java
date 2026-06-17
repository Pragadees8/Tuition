package com.tutor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutor.dto.NotificationDTO;
import com.tutor.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // SUPER ADMIN views notifications
    @GetMapping
    ("/super-admin")
    public List<NotificationDTO> getSuperAdminNotifications() {
        return notificationService.getSuperAdminNotifications();
    }
}
