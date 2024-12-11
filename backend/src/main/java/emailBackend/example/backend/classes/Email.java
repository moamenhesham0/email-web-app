package emailBackend.example.backend.classes;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Email implements Serializable{
    private static HashMap<String , Integer>  prioritiesToInt = new Hashmap<>();
    private static HashMap<Integer , String>  prioritiesToString = new Hashmap<>();
    static{
    prioritiesToInt.put("Urgent" , 4);
    prioritiesToInt.put("Important" , 3);
    prioritiesToInt.put("Moderate" , 2);
    prioritiesToInt.put("Minor" , 1);
    for (Map.Entry<String, Integer> en : prioritiesToInt.entrySet()) {
        String key = en.getKey();
        Integer val = en.getValue();
        prioritiesToString.put(val , key);
    }
    }
    private String sender;
    private String recipient;
    private String subject;
    private String textBody;
    private LocalDateTime timeStamp;
    private boolean read = false;
    private List<Attachment> attachments;
    private int priority; 
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
    public String getId() {
        return this.id;
    }
    
    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }
    public LocalDateTime getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
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
        this.attachments = attachments;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setPriority(String priority)
    {
        this.priority = prioritiesToInt.get(priority);
    }
    public int getPriorityInt()
    {
        return this.priority;
    }
    public String getPriorityString()
    {
        return prioritiesToString.get(this.priority);
    }
    
    
    @Override
    public String toString() {
        return "Email [recipient=" + recipient + ", subject=" + subject + ", textBody=" + textBody + ", timeStamp="
                + timeStamp + ", read=" + read + ", attachments=" + attachments + ", id=" + id + "]";
    }


    
    
    
}