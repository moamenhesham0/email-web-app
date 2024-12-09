package emailBackend.example.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.repository.EmailRepository;

@Service
public class EmailService {
    @Autowired
    private EmailRepository emailRepository;

    public void createEmail(Email email) {
        emailRepository.save(email);
    }

       public Optional<Email> getEmailById(String id) {
        return emailRepository.findById(id);
    }
}
