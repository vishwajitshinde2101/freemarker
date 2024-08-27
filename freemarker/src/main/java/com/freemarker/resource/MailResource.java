package com.freemarker.resource;

import com.freemarker.dto.MailRequest;
import com.freemarker.dto.MailResponse;
import com.freemarker.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MailResource {

    private final EmailService emailService;

    public MailResource(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-mail")
    public ResponseEntity<MailResponse> sendMail(@RequestBody MailRequest request) {
        MailResponse response = emailService.sendMail(request);
        return ResponseEntity.ok(response);
    }
}
