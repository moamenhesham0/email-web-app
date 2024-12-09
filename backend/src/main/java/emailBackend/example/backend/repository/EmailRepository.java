package emailBackend.example.backend.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


import org.springframework.stereotype.Repository;

import emailBackend.example.backend.classes.Email;

@Repository
public class EmailRepository {
    private final Map<String, Email> mails = new HashMap<>();

        // Save an email
    public Email save(Email email) {
        email.setId(UUID.randomUUID().toString()); 
        email.setTimeStamp(LocalDateTime.now());
        mails.put(email.getId(), email);
        System.out.println(email.toString());
        return email;
    }

    public Optional<Email> findById(String id) {   // optional used for avoid makeing if statment ex if (erc == null) 
        return Optional.ofNullable(mails.get(id));
    }

  
    public List<Email> findAll() {
        return new ArrayList<>(mails.values());
    }


}
