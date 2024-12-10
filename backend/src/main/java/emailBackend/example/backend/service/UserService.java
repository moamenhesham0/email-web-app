package emailBackend.example.backend.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository emailRepository;

    public void createEmail(Email email) {
        email.setId(UUID.randomUUID().toString()); 
        email.setTimeStamp(LocalDateTime.now());

        emailRepository.save(email);
    }

       public Optional<Email> getEmailById(String id) {
        return emailRepository.findById(id);
    }

    public void deleteEmailById(String id) {
       emailRepository.deleteEmailById(id);
    }
    
}
