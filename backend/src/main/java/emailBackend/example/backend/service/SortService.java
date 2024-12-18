package emailBackend.example.backend.service;

import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.classes.Sort.SortStrategy;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SortService {

    public List<Email> sortEmails(List<Email> emails, SortStrategy sortBy) {
        if (emails == null || emails.isEmpty()) {
            return Collections.emptyList();
        }

        // Apply the sorting strategy
        sortBy.sort(emails);
        return emails;
    }
}
