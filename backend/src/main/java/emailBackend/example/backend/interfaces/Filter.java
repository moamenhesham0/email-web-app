package emailBackend.example.backend.interfaces;
import java.util.List;

import emailBackend.example.backend.classes.Email;
public interface  Filter{
    List<Email> filter(List<Email> objects , String filterCriteria);
}
