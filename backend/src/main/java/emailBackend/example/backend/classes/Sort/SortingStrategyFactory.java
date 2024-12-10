package emailBackend.example.backend.classes.Sort;

public class SortingStrategyFactory {
    public SortingStrategy getStrategy(String name){
        if(name == null){
            return null;
        }

        return switch (name) {
            case "body" -> new BodySorting();
            case "subject" -> new SubjectSorting();
            case "sender" -> new SenderSorting();
            case "recipient" -> new RecipientSorting();
            case "priority" -> new PrioritySorting();
            case "timestamp" -> new TimestampSorting();
            default -> throw new IllegalArgumentException("There is no strategy with name " + name);
        };
    }
}
