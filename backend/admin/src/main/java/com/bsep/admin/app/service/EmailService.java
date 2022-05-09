package com.bsep.admin.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailVerificationMail(String toEmail, String token) {
        String path = "http://localhost:8080/email-verification/";
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setSubject("Kucni sigurnosni sistem - verifikacija mejla");
            MimeMessageHelper helper =  new MimeMessageHelper(msg,true);
            helper.setTo(toEmail);
            helper.setFrom("jo.jevtic999@gmail.com");
            helper.setSubject("Kucni sigurnosni sistem - verifikacija mejla");
            String message = "<h1>Verifikacija mejla</h1><p>Da biste verifikovali mejl, kliknite na sledeći link: " +
                    "<a href='" + path + token + "'>" + path + token + "</a>" + "</p>";
            helper.setText(message,true);
            javaMailSender.send(msg);
        } catch (MessagingException ex) {
            System.out.println("Error while sending email");
        }
    }
}
