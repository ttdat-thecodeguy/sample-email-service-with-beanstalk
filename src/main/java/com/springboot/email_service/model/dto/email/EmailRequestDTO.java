package com.springboot.email_service.model.dto.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class EmailRequestDTO {
    @Email
    @NotBlank
    private String to;

    private String cc;
    private String bcc;

    @NotBlank
    private String subject;

    @NotBlank
    private String body; // can be plain text or HTML depending on endpoint

    private List<String> attachments; // file paths or base64 (depends on your approach)
}
