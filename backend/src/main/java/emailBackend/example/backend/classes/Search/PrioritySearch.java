package emailBackend.example.backend.classes.Search;

import emailBackend.example.backend.classes.Email;
import java.util.List;
import java.util.stream.Collectors;

public class PrioritySearch implements SearchStrategy {
    @Override
    public List<Email> search(List<Email> emails, String keyword) {
        int priorityLevel;
        try {
            priorityLevel = Integer.parseInt(keyword);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Keyword must be a valid number for priority search.");
        }

        return emails.stream()
                .filter(email -> email.getPriority() == priorityLevel)
                .collect(Collectors.toList());
    }
}
