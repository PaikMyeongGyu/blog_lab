package com.email.email.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static jakarta.mail.Message.RecipientType.TO;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.email}")
    private String EMAIL_ID;
    public static final String SUBJECT = "인증코드 확인 안내";
    public static final String AUTH_URL = "http:/localhost:8080/emailVerification/";

    @Async("emailExecutor")
    public void sendVerificationEmail(String email, String token) {
        try {
            sendVerificationEmailMessage(email, token);
        } catch (MessagingException e) {
            log.error("{} : 이메일 전송 요청 실패", email);
        }
    }

    protected void sendVerificationEmailMessage(String toEmail, String token) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(EMAIL_ID);
        message.setRecipients(TO, toEmail);
        message.setSubject(SUBJECT);
        message.setText(AUTH_URL + token);
        mailSender.send(message);
    }
}
