package com.ssc.rwa.serviceImpl;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ssc.rwa.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final ResourceLoader resourceLoader;

    @Override
    public void sendEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true); // true = HTML

        mailSender.send(message);
    }

    @Override
    public String buildEmailFromTemplate(String templateName, Map<String, String> placeholders) {
        try {
            // Template path: src/main/resources/templates/email/{templateName}.html
            Resource resource = resourceLoader.getResource("classpath:templates/" + templateName + ".html");

            String template;
            try (InputStream inputStream = resource.getInputStream()) {
                template = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }

            // Replace placeholders
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                String placeholder = "${" + entry.getKey() + "}";
                template = template.replace(placeholder, entry.getValue());
            }

            return template;

        } catch (Exception e) {
            log.error("Error reading or processing email template: {}", templateName, e);
            throw new RuntimeException("Failed to load email template", e);
        }
    }
}
