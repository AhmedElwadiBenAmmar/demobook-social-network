package com.wadi.booknetwork.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String confirmationUrl,   // Doit être une URL complète, ex: https://monapp.com/activate?code=xyz
            String activationCode,
            String subject
    ) throws MessagingException {

        // Préparer les variables pour Thymeleaf
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activation_code", activationCode);

        Context context = new Context();
        context.setVariables(properties);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("contact@ahmedElwadi.com");
        helper.setTo(to);
        helper.setSubject(subject);

        // Générer le contenu HTML à partir du template et du contexte
        String htmlContent = templateEngine.process(emailTemplate.getName(), context);

        helper.setText(htmlContent, true); // true = HTML

        mailSender.send(mimeMessage);
    }
}
