package emailBackend.example.backend.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Folder implements Serializable {
    
    String folderName ;
    int readEmails =0 ;
    int unreadEmails = 0;
    int EmailCount = 0;
    List<Email> Emails = new ArrayList<>();

    public Folder(){}
    
    public Folder(String folderName, List<Email> emails) {
        this.folderName = folderName;
        Emails = emails;
    }
    public String getFolderName() {
        return folderName;
    }
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
    public int getReadEmails() {
        return readEmails;
    }
    public void setReadEmails(int readEmails) {
        this.readEmails = readEmails;
    }
    public int getUnreadEmails() {
        return unreadEmails;
    }
    public void setUnreadEmails(int unreadEmails) {
        this.unreadEmails = unreadEmails;
    }
    public int getEmailCount() {
        return EmailCount;
    }
    public void setEmailCount(int emailCount) {
        EmailCount = emailCount;
    }
    public List<Email> getEmails() {
        return Emails;
    }
    public void setEmails(List<Email> emails) {
        Emails = emails;
    }
    
    
    @Override
    public String toString() {
        return "Folder [folderName=" + folderName + ", readEmails=" + readEmails + ", unreadEmails=" + unreadEmails
                + ", EmailCount=" + EmailCount + ", Emails=" + Emails + "]";
    } 

    
}
