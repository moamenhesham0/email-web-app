package emailPackend.example.backend.classes;
import java.util.*;



class Email {
       
    private String id;
    private String sender;
    private String recipient;
    private String subject;
    private String textBody;
    private Date timeStamp;
    private boolean read = false;
    private List<Attachment> attachments;

    public Email(Builder builder)
    {
        this.sender = builder.sender;
        this.recipient = builder.recipient;
        this.subject = builder.subject;
        this.textBody = builder.textBody;
        this.timeStamp = builder.timeStamp;
        this.read = false;
        this.attachments = builder.attachments;
    }
    
    public static Builder{
        
    }

}
