package emailBackend.example.backend.service;

import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.classes.Search.SearchStrategy;
import emailBackend.example.backend.repository.Repositories;
import emailBackend.example.backend.classes.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private Repositories repositories;

    public List<Email> searchEmails(User profile, String folderName, SearchStrategy searchStrategy, String keyword) {
        int folderIndex = repositories.getFolderByName(profile, folderName);

        List<Email> emails = profile.getFolders().get(folderIndex).getEmails();
        if (emails == null || emails.isEmpty()) {
            return Collections.emptyList();
        }

        // Apply the search strategy's `search` method
        return searchStrategy.search(emails, keyword);
    }
}
