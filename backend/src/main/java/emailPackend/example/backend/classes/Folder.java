package emailPackend.example.backend.classes;
import java.util.*;
class Folder {
    private String folderName;
    private int readEmails;
    private int unreadEmails;
    private int emailsCount;
    private List<Email> Emails;
    private SortingEngine sortingEngine;

    public void addEmail(Email email)
    {
        Emails.add(email);
    }
    public void sort(String sortingMethod)
    {
        
    }

}
