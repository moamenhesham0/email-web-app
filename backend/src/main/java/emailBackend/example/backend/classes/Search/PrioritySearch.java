package emailBackend.example.backend.classes.Search;

import java.util.List;
import java.util.stream.Collectors;

import emailBackend.example.backend.classes.Email;

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
                .filter(email -> email.getPriorityInt() == priorityLevel)
                .collect(Collectors.toList());
    }
}
