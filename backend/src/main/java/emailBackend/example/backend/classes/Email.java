package emailBackend.example.backend.classes;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Email implements Serializable{
    private String recipient;
    private String subject;
    private String textBody;
    private LocalDateTime timeStamp;
    private boolean read = false;
    private List<Attachment> attachments;
    private String id ;
    
    public String getRecipient() {
        return recipient;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getTextBody() {
        return textBody;
    }
    public String getId() {
        return id;
    }
    
    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
    public boolean isRead() {
        return read;
    }
    public void setRead(boolean read) {
        this.read = read;
    }
    public List<Attachment> getAttachments() {
        return attachments;
    }
    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    
    @Override
    public String toString() {
        return "Email [recipient=" + recipient + ", subject=" + subject + ", textBody=" + textBody + ", timeStamp="
                + timeStamp + ", read=" + read + ", attachments=" + attachments + ", id=" + id + "]";
    }


    
    
    
}