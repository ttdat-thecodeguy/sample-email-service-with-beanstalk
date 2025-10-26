package com.springboot.email_service.service.impl;

import com.springboot.email_service.model.dto.email.EmailRequestDTO;
import com.springboot.email_service.service.IEmailService;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendSimpleEmail(EmailRequestDTO request) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(request.getTo());
        if (request.getCc() != null) msg.setCc(request.getCc());
        if (request.getBcc() != null) msg.setBcc(request.getBcc());
        msg.setSubject(request.getSubject());
        msg.setText(request.getBody());
        mailSender.send(msg);
    }

    @Override
    public void sendHtmlEmail(EmailRequestDTO request) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setTo(request.getTo());
            if (request.getCc() != null) helper.setCc(request.getCc());
            if (request.getBcc() != null) helper.setBcc(request.getBcc());
            helper.setSubject(request.getSubject());
            helper.setText(request.getBody(), true); // true = HTML
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send HTML email", e);
        }
    }

    @Override
    public void sendEmailWithAttachment(EmailRequestDTO request, Resource attachment) {
//        MimeMessage message = mailSender.createMimeMessage();
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
//            helper.setTo(request.getTo());
//            helper.setSubject(request.getSubject());
//            helper.setText(request.getBody(), true);
//            if (!defaultFrom.isBlank()) helper.setFrom(defaultFrom);
//            if (attachment != null && attachment.exists()) {
//                helper.addAttachment(attachment.getFilename(), attachment);
//            }
//            mailSender.send(message);
//        } catch (MessagingException e) {
//            throw new RuntimeException("Failed to send email with attachment", e);
//        }
    }

    @Override
    public void sendTemplateEmail(String to, String subject, String templateName, Map<String, Object> variables) {
        Context ctx = new Context();
        ctx.setVariables(variables);
        String body = templateEngine.process(templateName, ctx);

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send template email", e);
        }
    }
}
