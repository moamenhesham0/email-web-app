package emailBackend.example.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emailBackend.example.backend.classes.Attachment;
import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.service.EmailService;


@RestController
@RequestMapping("/api/emails")
public class EmailController {

     @Autowired
    private EmailService emailService;

    @GetMapping("/{id}")
    public Optional<Email> getEmail(@PathVariable String id) {
       
        return emailService.getEmailById(id);
    }
    @PostMapping("/send/{email}/{subject}/{textBody}/{attachment}")
    public void sendEmail(@PathVariable String email, @PathVariable String subject, @PathVariable String textBody, @PathVariable List<Attachment> attachment){
        Email makeEmail = new Email();
        makeEmail.setRecipient(email);
        makeEmail.setSubject(subject);
        makeEmail.setTextBody(textBody);
        makeEmail.setAttachments(attachment);
        emailService.createEmail(makeEmail);
    }

}
