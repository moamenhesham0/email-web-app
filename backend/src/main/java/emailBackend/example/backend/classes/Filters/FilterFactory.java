package emailBackend.example.backend.classes.Filters;
import org.springframework.stereotype.Component;

import emailBackend.example.backend.interfaces.Filter;
@Component
public class FilterFactory {
    public Filter getFilter(String name){
        if(name == null){
            return null;
        }

        return switch (name) {
            case "attachment" -> new AttachmentFilter();
            case "subject" -> new SubjectFilter();
            case "sender" -> new SenderFilter();
            case "priority" -> new PriorityFilter();
            default -> throw new IllegalArgumentException("There is no strategy with name " + name);
        };
    }
}
