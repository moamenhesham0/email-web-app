package emailBackend.example.backend.classes.Search;

import emailBackend.example.backend.classes.Email;
import java.util.List;

public interface SearchStrategy {
    List<Email> search(List<Email> emails, String keyword);
}
