package com.service.upay_services_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class userEmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationEmail(String to, String name, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to Upay Services - Your Account Details");
        message.setText("Hello " + name + ",\n\n"
                + "Your account has been created successfully.\n"
                + "Here are your login credentials:\n\n"
                + "Email: " + to + "\n"
                + "Password: " + password + "\n\n"
                + "Please change your password after first login.\n\n"
                + "Regards,\nUpay Services Team");

        mailSender.send(message);
    }
}
