package emailBackend.example.backend.interfaces;
import java.util.List;
interface  Filter {
    List<Object> filter(List<Object> objects , Object filterCriteria);
}
