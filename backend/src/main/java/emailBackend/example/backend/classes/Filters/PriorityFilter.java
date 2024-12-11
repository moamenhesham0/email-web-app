package emailBackend.example.backend.classes.Filters;

import java.util.ArrayList;
import java.util.List;

import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.interfaces.Filter;

public class PriorityFilter implements Filter{
    @Override
    public List<Email> filter(List<Email> emails , String priority)
    {
        List<Email> filteredEmails = new ArrayList<>();
        for(Email email : emails)
        {
            if(email.getPriorityString().equalsIgnoreCase(priority))
                filteredEmails.add(email);
        }
        return filteredEmails;
    } 
}
