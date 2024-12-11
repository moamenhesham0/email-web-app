package emailBackend.example.backend.classes.Search;

import emailBackend.example.backend.classes.Email;
import java.util.List;
import java.util.stream.Collectors;

public class AttachmentsSearch implements SearchStrategy {
    @Override
    public List<Email> search(List<Email> emails, String keyword) {
        int minAttachments;
        try {
            minAttachments = Integer.parseInt(keyword);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Keyword must be a valid number for attachments search.");
        }

        return emails.stream()
                .filter(email -> email.getAttachments() != null && email.getAttachments().size() >= minAttachments)
                .collect(Collectors.toList());
    }
}
