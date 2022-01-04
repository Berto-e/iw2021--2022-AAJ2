package com.example.application.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService{

    @Autowired
    private JavaMailSender emailSender;

    public void SendSimpleMessage(
            String to, String subject,String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("discovercinemaservice@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

}
