package emailBackend.example.backend.classes.Sort;

import java.util.Comparator;
import java.util.List;

import emailBackend.example.backend.classes.Email;

public class PrioritySort implements SortStrategy {
    @Override
    public void sort(List<Email> emails) {
        emails.sort(Comparator.comparing(email -> email.getPriority().getPriorityInt()));
    } 
}