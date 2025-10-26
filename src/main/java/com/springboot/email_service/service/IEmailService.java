package com.springboot.email_service.service;

import com.springboot.email_service.model.dto.email.EmailRequestDTO;
import jakarta.annotation.Resource;

import java.util.Map;

public interface IEmailService {
    void sendSimpleEmail(EmailRequestDTO request);
    void sendHtmlEmail(EmailRequestDTO request);
    void sendEmailWithAttachment(EmailRequestDTO request, Resource attachment);
    void sendTemplateEmail(String to, String subject, String templateName, Map<String, Object> variables);
}
