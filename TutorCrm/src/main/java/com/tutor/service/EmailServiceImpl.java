package com.tutor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendLoginEmail(String toEmail,
                               String name,
                               String role,
                               boolean isFirstLogin) {

        String subject;
        String message;

        if (isFirstLogin) {

            subject = "🎉 Welcome to Tuition System";

            message = "Hello " + name + ",\n\n"
                    + "Welcome to Tuition System!\n"
                    + "Role: " + role + "\n\n"
                    + "We are happy to have you onboard.\n\n"
                    + "Regards,\nTuition Team";

        } else {

            subject = "Login Alert - Tuition System";

            message = "Hello " + name + ",\n\n"
                    + "You have successfully logged in.\n"
                    + "Role: " + role + "\n\n"
                    + "If this was not you, contact admin.\n\n"
                    + "Regards,\nTuition Team";
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}