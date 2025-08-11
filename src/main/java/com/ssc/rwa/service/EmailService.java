package com.ssc.rwa.service;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmail(String to, String subject, String content) throws MessagingException;

    /**
     * Loads an HTML email template and replaces placeholders with actual values.
     *
     * @param templateName The name of the HTML template (without path).
     * @param placeholders Key-value pairs to replace in the template.
     * @return Final HTML content as a string.
     */
    String buildEmailFromTemplate(String templateName, java.util.Map<String, String> placeholders);
}
