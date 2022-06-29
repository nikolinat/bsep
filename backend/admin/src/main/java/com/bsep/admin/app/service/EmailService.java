package com.bsep.admin.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bsep.admin.app.annotation.LogAfterReturning;
import com.bsep.admin.app.annotation.LogAfterThrowing;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @LogAfterThrowing(message = "ERROR sending email for email verification")
    @LogAfterReturning(message = "SUCCESS sending email for email verification")
    public void sendEmailVerificationMail(String toEmail, String token) {
        String path = "https://localhost:8080/email-verification/";
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setSubject("Kucni sigurnosni sistem - verifikacija mejla");
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toEmail);
            helper.setFrom("bezbednost");
            helper.setSubject("Kucni sigurnosni sistem - verifikacija mejla");
            String message = "<h1>Verifikacija mejla</h1><p>Da biste verifikovali mejl, kliknite na sledeći link: " +
                    "<a href='" + path + token + "'>" + path + token + "</a>" + "</p>";
            helper.setText(message, true);
            javaMailSender.send(msg);
        } catch (MessagingException ex) {
            System.out.println("Error while sending email");
        }
    }

    @LogAfterThrowing(message = "ERROR sending email for blocked account")
    @LogAfterReturning(message = "SUCCESS sending email for blocked account")
    public void sendEmailForBlockedAccount(String toEmail) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setSubject("Kucni sigurnosni sistem - blokiran nalog");
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toEmail);
            helper.setFrom("bezbednost");
            helper.setSubject("Kucni sigurnosni sistem - blokiran nalog");
            String message = "Vaš nalog je blokiran, zbog neuspešnog logovanja više puta.";
            helper.setText(message, true);
            javaMailSender.send(msg);
        } catch (MessagingException ex) {
            System.out.println("Error while sending email");
        }
    }

    @LogAfterThrowing(message = "ERROR sending certificate")
    @LogAfterReturning(message = "SUCCESS sending certificate")
    public void sendCertificate(String toEmail, String path) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setSubject("Kucni sigurnosni sistem - sertifikat");
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toEmail);
            helper.setFrom("bezbednost");
            helper.setSubject("Kucni sigurnosni sistem - sertifikat");
            Multipart emailContent = new MimeMultipart();
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("U prilogu se nalazi Vaš sertifikat!");
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(path);

            emailContent.addBodyPart(textBodyPart);
            emailContent.addBodyPart(attachmentBodyPart);

            msg.setContent(emailContent);

            javaMailSender.send(msg);

        } catch (MessagingException | IOException ex) {
            System.out.println("Greška prilikom slanja!");
        }
    }
}
