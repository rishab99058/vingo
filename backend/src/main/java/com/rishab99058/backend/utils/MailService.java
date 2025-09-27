package com.rishab99058.backend.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;


    public void sendForgotPasswordOtp(String toEmail, String otp) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        String htmlMsg = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "  <style>" +
                "    body { font-family: Arial, sans-serif; background-color: #f4f4f4; }" +
                "    .container { background-color: #ffffff; padding: 20px; margin: 20px auto; width: 90%; max-width: 500px; border-radius: 10px; box-shadow: 0px 4px 6px rgba(0,0,0,0.1); }" +
                "    h2 { color: #333333; }" +
                "    p { color: #555555; font-size: 16px; }" +
                "    .otp { font-size: 24px; font-weight: bold; color: #1a73e8; margin: 20px 0; }" +
                "    .footer { font-size: 12px; color: #999999; margin-top: 20px; }" +
                "  </style>" +
                "</head>" +
                "<body>" +
                "  <div class='container'>" +
                "    <h2>Team Vingo</h2>" +
                "    <p>Hello,</p>" +
                "    <p>We received a request to reset your password. Use the OTP below to proceed. It is valid for the next 10 minutes.</p>" +
                "    <div class='otp'>" + otp + "</div>" +
                "    <p>If you did not request this, please ignore this email.</p>" +
                "    <p class='footer'>This is an automated message from Vingo. Please do not reply.</p>" +
                "  </div>" +
                "</body>" +
                "</html>";

        helper.setTo(toEmail);
        helper.setSubject("Reset Your Vingo Password - OTP");
        helper.setText(htmlMsg, true);

        mailSender.send(message);
    }
}

