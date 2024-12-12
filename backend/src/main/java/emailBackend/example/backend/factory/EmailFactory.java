package emailBackend.example.backend.factory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import emailBackend.example.backend.classes.Attachment;
import emailBackend.example.backend.classes.Email;

@Component
public class EmailFactory {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    public Email createEmail(String senderEmail, String recipientEmail, String subject, String textBody, List<Attachment> attachments) {
        Email email = new Email();
        email.setSender(senderEmail);
        email.setRecipient(recipientEmail);
        email.setSubject(subject);
        email.setTextBody(textBody);
        email.setAttachments(attachments);
        email.setId(UUID.randomUUID().toString());
        email.setTimeStamp(ZonedDateTime.now().format(FORMATTER)); // Format LocalDateTime
        return email;
    }
}
