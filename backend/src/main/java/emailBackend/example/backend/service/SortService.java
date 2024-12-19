package emailBackend.example.backend.service;

import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.classes.User;
import emailBackend.example.backend.classes.Sort.SortStrategy;
import emailBackend.example.backend.repository.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SortService {

    @Autowired
    Repositories repositories;

    public List<Email> sortEmails(User profile, String folderName, SortStrategy sortBy) {

        int indexOfFolder = repositories.getFolderByName(profile, folderName);
        
        List<Email> emails = profile.getFolders().get(indexOfFolder).getEmails();        
        if (emails == null || emails.isEmpty()) {
            return Collections.emptyList();
        }

         List<Email> emailsCopy = new ArrayList<>(emails);
         sortBy.sort(emailsCopy);
        // Apply the sorting strategy
        return emailsCopy;
    }
}
