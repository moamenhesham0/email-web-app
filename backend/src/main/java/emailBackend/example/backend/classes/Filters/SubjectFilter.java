package emailBackend.example.backend.classes.Filters;

import java.util.ArrayList;
import java.util.List;

import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.interfaces.Filter;
public class SubjectFilter implements Filter {
    @Override
    public List<Email> filter(List<Email> emails , String subject)
    {
        List<Email> filteredEmails = new ArrayList<>();
        for(Email email : emails)
        {
            if(email.getSubject().equalsIgnoreCase(subject))
                filteredEmails.add(email);
        }


        return filteredEmails;
    }
}
