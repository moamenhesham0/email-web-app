package emailBackend.example.backend.classes.Filters;

import java.util.*;
import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.interfaces.Filter;

class AttachmentFilter implements Filter<Email , String> {
    @Override
    public List<Email> filter(List<Email> emails , String attachmentType)
    {
        List<Email> filteredEmails = new ArrayList<>();
        for(Email email : emails)
        {
            if(email.getAttachments().equalsIgnoreCase(attachmentType))
                filteredEmails.add(email);
        }
        return filteredEmails;
    }
    
}
