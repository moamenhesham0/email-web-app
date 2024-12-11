package emailBackend.example.backend.service;
import java.util.List;

import org.springframework.stereotype.Service;

import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.classes.Filters.AttachmentFilter;
import emailBackend.example.backend.classes.Filters.PriorityFilter;
import emailBackend.example.backend.classes.Filters.SenderFilter;
import emailBackend.example.backend.classes.Filters.SubjectFilter;
import emailBackend.example.backend.interfaces.Filter;

@Service
public class FiltersService {


    private static Filter senderFilter = new SenderFilter();
    private static Filter attachmentFilter = new AttachmentFilter();
    private static Filter priorityFilter  = new PriorityFilter();
    private static Filter subjectFilter = new SubjectFilter();
    public List<Email> filterBySender(List<Email> emails , String sender)
    {
        return senderFilter.filter(emails, sender);
    }

    public List<Email> filterByPriority(List<Email> emails , String priority)
    {
        return priorityFilter.filter(emails, priority);
    }

    public List<Email> filterByAttachment(List<Email> emails , String attachment)
    {
        return attachmentFilter.filter(emails, attachment);
    }
    public List<Email> filterBySubject(List<Email> emails , String subject)
    {
        return subjectFilter.filter(emails, subject);
    }
}
