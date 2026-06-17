package com.tutor.service;

public interface EmailService {

    void sendLoginEmail(String toEmail,
                        String name,
                        String role,
                        boolean isFirstLogin);
}