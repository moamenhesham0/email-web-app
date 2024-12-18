package emailBackend.example.backend.classes.Search;

import emailBackend.example.backend.classes.Email;


import java.util.List;
import java.util.stream.Collectors;

public class AttachmentsSearch implements SearchStrategy {
    @Override
    public List<Email> search(List<Email> emails, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Keyword must not be null or empty for attachment name search.");
        }

        // Search for emails where any attachment's name contains the keyword
        return emails.stream()
                .filter(email -> email.getAttachments() != null &&
                        email.getAttachments().stream()
                                .anyMatch(attachment -> attachment.getAttName() != null &&
                                        attachment.getAttName().toLowerCase().contains(keyword.toLowerCase())))
                .collect(Collectors.toList());
    }
}
