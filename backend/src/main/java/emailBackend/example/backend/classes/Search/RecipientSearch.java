package emailBackend.example.backend.classes.Search;

import emailBackend.example.backend.classes.Email;
import java.util.List;
import java.util.stream.Collectors;

public class RecipientSearch implements SearchStrategy {
    @Override
    public List<Email> search(List<Email> emails, String keyword) {
        return emails.stream()
                .filter(email -> email.getRecipient() != null && email.getRecipient().contains(keyword))
                .collect(Collectors.toList());
    }
}
