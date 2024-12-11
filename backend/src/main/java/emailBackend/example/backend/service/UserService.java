package emailBackend.example.backend.service;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import emailBackend.example.backend.classes.Attachment;
import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.factory.EmailFactory;
import emailBackend.example.backend.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository emailRepository;

    @Autowired
    private EmailFactory emailFactory;
    
    public void createEmail(String senderEmail,String recipientEmail, String subject, String textBody, List<Attachment> attachments) {
        Email email = emailFactory.createEmail(senderEmail, recipientEmail,subject ,textBody, attachments);
        emailRepository.save(email);
    }

       public Optional<Email> getEmailById(String id) {
        return emailRepository.findById(id);
    }

    public void deleteEmailById(String id) {
       emailRepository.deleteEmailById(id);
    }

    public void deleteFolderByName(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteFolderByName'");
    }

    
}
