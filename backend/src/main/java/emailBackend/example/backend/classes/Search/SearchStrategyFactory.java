package emailBackend.example.backend.classes.Search;
import org.springframework.stereotype.Component;

@Component
public class SearchStrategyFactory {
    public SearchStrategy getStrategy(String name) {
        if (name == null) {
            return null;
        }
        return switch (name.toLowerCase()) {
            case "body" -> new BodySearch();
            case "subject" -> new SubjectSearch();
            case "sender" -> new SenderSearch();
            case "recipient" -> new RecipientSearch();
            case "attachments" -> new AttachmentsSearch();
            case "priority" -> new PrioritySearch();
            default -> throw new IllegalArgumentException("There is no strategy with name " + name);
        };
    }
}
