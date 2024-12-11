package emailBackend.example.backend.classes.Sort;

public class SortStrategyFactory {
    public SortStrategy getStrategy(String name){
        if(name == null){
            return null;
        }

        return switch (name) {
            case "body" -> new BodySort();
            case "subject" -> new SubjectSort();
            case "sender" -> new SenderSort();
            case "recipient" -> new RecipientSort();
            case "priority" -> new PrioritySort();
            case "timestamp" -> new DateSort();
            case "Attachments" -> new AttachementsSort();
            default -> throw new IllegalArgumentException("There is no strategy with name " + name);
        };
    }
}
