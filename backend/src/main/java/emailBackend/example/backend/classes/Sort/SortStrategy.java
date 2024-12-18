package emailBackend.example.backend.classes.Sort;

import java.util.List;
import emailBackend.example.backend.classes.*;

public interface SortStrategy {
    void sort(List<Email> emails);
}
