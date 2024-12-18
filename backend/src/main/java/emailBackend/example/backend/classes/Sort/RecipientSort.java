package emailBackend.example.backend.classes.Sort;

import java.util.Comparator;
import java.util.List;

import emailBackend.example.backend.classes.Email;

public class RecipientSort implements SortStrategy {
    @Override
    public void sort(List<Email> emails) {
        emails.sort(Comparator.comparing(Email::getRecipient));
    } 
}