package com.vlad.contact.contact_backend.service;

import com.vlad.contact.contact_backend.dto.ContactRequest;
import com.vlad.contact.contact_backend.exception.EmailSendingException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final String RECIPIENT = "vpodymskiy@icloud.com";
    private static final String SUBJECT = "Vladka. Job offer?";

    private final JavaMailSender mailSender;

    public void sendContactMessage(ContactRequest request) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            helper.setTo(RECIPIENT);
            helper.setReplyTo(request.email());
            helper.setSubject(SUBJECT);
            helper.setText(buildBody(request), false);

            mailSender.send(message);
        } catch (MessagingException | MailException e) {
            throw new EmailSendingException("No se ha podido enviar el correo de contacto", e);
        }
    }

    private String buildBody(ContactRequest request) {
        String phone = request.phone() == null || request.phone().isBlank() ? "-" : request.phone();
        return String.format("Имя: %s%nТелефон: %s%nEmail: %s%nСообщение:%n%s",
                request.name(), phone, request.email(), request.message());
    }
}
