package com.luheresbar.daily.web.mailManager;

import jakarta.mail.MessagingException;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailManager {

    private final JavaMailSender javaMailSender;

    public MailManager(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    String content = MessageHTMLUtil.TEMPLATE_PRUEBA;

    public void sendEmail(String email, String mailSubject, String code) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject(mailSubject);

        String htmlContent = setContentInTemplate(content, 0, code);

        helper.setText(htmlContent, true);

        javaMailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String setContentInTemplate(String templateCode, int index, String code) {
        return templateCode.replace("{" + index + "}", code);
    }
}