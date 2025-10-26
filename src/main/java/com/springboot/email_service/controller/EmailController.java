package com.springboot.email_service.controller;

import com.springboot.email_service.model.dto.email.EmailRequestDTO;
import com.springboot.email_service.service.IEmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("emails")
public class EmailController {
    private final IEmailService emailService;

    @PostMapping("/send/simple")
    public void sendSimple(@Valid @RequestBody EmailRequestDTO request) {
        emailService.sendSimpleEmail(request);
    }

    @PostMapping(value = "/send/html")
    public void sendHtml(@Valid @RequestBody EmailRequestDTO request) {
        emailService.sendHtmlEmail(request);
    }

//    @PostMapping(value = "/send/attachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public void sendWithAttachment(
//            @RequestPart("meta") @Valid EmailRequestDTO request,
//            @RequestPart("file") MultipartFile file) throws Exception {
//
//        ByteArrayResource resource = new ByteArrayResource(file.getBytes()){
//            @Override
//            public String getFilename(){ return file.getOriginalFilename(); }
//        };
//        emailService.sendEmailWithAttachment(request, resource);
//    }

    @PostMapping("/send/template")
    public void sendTemplate(@RequestParam String to,
                             @RequestParam String subject,
                             @RequestBody Map<String, Object> variables) {
        emailService.sendTemplateEmail(to, subject, "email-template", variables);
    }
}
