package emailBackend.example.backend.classes;

import java.util.List;

public class Folder {
    
    String folderName ;
    int readEmails;
    int unreadEmails;
    int EmailCount;
    List<Email> Emails;
    
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
