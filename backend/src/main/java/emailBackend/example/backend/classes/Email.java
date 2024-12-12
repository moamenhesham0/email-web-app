package emailBackend.example.backend.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class Email implements Serializable{
   
    private String sender;
    private String recipient;
    private String subject;
    private String textBody;
    private String timeStamp;
    private boolean read = false;
    private List<Attachment> attachments;
    private Priority priority; 
    private String id ;

    public String getSender() {
        return this.sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return this.recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTextBody() {
        return this.textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isRead() {
        return this.read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public List<Attachment> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments != null ? attachments : new ArrayList<>();
    }

    public Priority getPriority() {
        return this.priority;
    }

    @Override
    public String toString() {
        return "Email [recipient=" + recipient + ", subject=" + subject + ", textBody=" + textBody + ", timeStamp="
                + timeStamp + ", read=" + read + ", attachments=" + attachments + ", id=" + id + "]";
    }
}