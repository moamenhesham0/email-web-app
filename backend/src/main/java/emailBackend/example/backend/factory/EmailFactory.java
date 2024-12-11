package emailBackend.example.backend.factory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


import org.springframework.stereotype.Component;

import emailBackend.example.backend.classes.Attachment;
import emailBackend.example.backend.classes.Email;

@Component
public class EmailFactory {
   
    public Email createEmail(String senderEmail,String recipientEmail, String subject, String textBody, List<Attachment> attachments) {
        
        
        Email email = new Email();
        email.setSender(senderEmail);
        email.setRecipient(recipientEmail);
        email.setSubject(subject);
        email.setTextBody(textBody);
        email.setAttachments(attachments);
        email.setId(UUID.randomUUID().toString()); 
        email.setTimeStamp(LocalDateTime.now());
        return email;
    }
}
