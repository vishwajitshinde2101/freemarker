package com.freemarker.service.impl;

import com.freemarker.dto.MailRequest;
import com.freemarker.dto.MailResponse;
import com.freemarker.service.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    private final Configuration freeMarkerConfig;

    public EmailServiceImpl(JavaMailSender javaMailSender, Configuration freeMarkerConfig) {
        this.javaMailSender = javaMailSender;
        this.freeMarkerConfig = freeMarkerConfig;
    }

    @Override
    public MailResponse sendMail(MailRequest request) {
        MailResponse response = new MailResponse();
        MimeMessage message = javaMailSender.createMimeMessage();
        Map<String, Object> model = new HashMap<>();
        model.put("name", request.getModel().get("name"));
        model.put("location", request.getModel().get("location"));
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            Template template = freeMarkerConfig.getTemplate("email.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            messageHelper.setFrom(request.getFrom());
            messageHelper.setTo(request.getTo());
            messageHelper.setSubject(request.getSubject());
            messageHelper.setText(html, true);

            javaMailSender.send(message);
            response.setMessage("Mail send to: " + request.getTo());
            response.setStatus(Boolean.TRUE);

        } catch (MessagingException | IOException | TemplateException e) {
            response.setMessage("Mail sending failure");
            response.setStatus(Boolean.FALSE);
        }
        return response;
    }
}
