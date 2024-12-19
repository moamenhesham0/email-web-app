package emailBackend.example.backend.service;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.classes.User;
import emailBackend.example.backend.interfaces.Filter;

import emailBackend.example.backend.repository.Repositories;


import org.springframework.beans.factory.annotation.Autowired;


@Service
public class FiltersService {

    @Autowired
    Repositories repositories;
   
    public List<Email> filterEmails(User profile, String folderName, Filter filter, String keyword) {
        int folderIndex = repositories.getFolderByName(profile, folderName);

        List<Email> emails = profile.getFolders().get(folderIndex).getEmails();
        if (emails == null || emails.isEmpty()) {
            return Collections.emptyList();
        }

        // Apply the search strategy's `search` method
        return filter.filter(emails, keyword);
    }
}
