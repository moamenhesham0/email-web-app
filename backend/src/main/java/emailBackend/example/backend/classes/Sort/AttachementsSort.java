package emailBackend.example.backend.classes.Sort;

import emailBackend.example.backend.classes.*;
import java.util.Comparator;
import java.util.List;

public class AttachementsSort implements SortStrategy {
    @Override
    public void sort(List<Email> emails) {
        emails.sort(Comparator.comparingInt(email -> email.getAttachments().size()));
    } 
}