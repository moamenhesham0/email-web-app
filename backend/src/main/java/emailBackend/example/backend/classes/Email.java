package emailBackend.example.backend.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Email implements Serializable {

    private String sender;
    private String recipient;
    private String subject;
    private String textBody;
    private String timeStamp;
    private boolean read = false;
    private List<Attachment> attachments;
    private Priority priority;
    private String id;

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

    public void setPriority(Priority priority) {
        this.priority = priority;
    }


    public static class Builder {

        private Email email;

        public Builder() {
            email = new Email();
        }

        public Builder setSender(String sender) {
            email.setSender(sender); // Use setter
            return this;
        }

        public Builder setRecipient(String recipient) {
            email.setRecipient(recipient); // Use setter
            return this;
        }

        public Builder setSubject(String subject) {
            email.setSubject(subject); // Use setter
            return this;
        }

        public Builder setTextBody(String textBody) {
            email.setTextBody(textBody); // Use setter
            return this;
        }

        public Builder setTimeStamp(String timeStamp) {
            email.setTimeStamp(timeStamp); // Use setter
            return this;
        }

        public Builder setRead(boolean read) {
            email.setRead(read); // Use setter
            return this;
        }

        public Builder setAttachments(List<Attachment> attachments) {
            email.setAttachments(attachments); // Use setter
            return this;
        }

        public Builder setPriority(Priority priority) {
            email.setPriority(priority); // Use setter
            return this;
        }

        public Builder setId(String id) {
            email.setId(id); // Use setter
            return this;
        }

        public Email build() {
            return email;
        }
    }
}