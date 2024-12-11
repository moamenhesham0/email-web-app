package emailBackend.example.backend.classes.Filters;

import java.util.*;
import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.interfaces.Filter;

public class SenderFilter implements Filter {
    @Override
    public List<Email> filter(List<Email> emails , String sender)
    {
        List<Email> filteredEmails = new ArrayList<>();
        for(Email email : emails)
        {
            if(email.getSender().equalsIgnoreCase(sender))
                filteredEmails.add(email);
        }
        return filteredEmails;
    }
}
