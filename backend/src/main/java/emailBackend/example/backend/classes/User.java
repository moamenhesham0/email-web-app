package emailBackend.example.backend.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class User implements Serializable {
    String username;
    String password; 
    String emailAddress; 
    List<Folder> folders;
    List<Contact> contacts;
    public User() {}
    
    public User(String username, String password, String emailAddress, List<Folder> folders, List<Contact> contacts) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.folders = folders;
        this.contacts = contacts;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public List<Folder> getFolders() {
        return folders;
    }
    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }
    

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password + ", emailAddress=" + emailAddress + ", folders="
                + folders + ", contacts=" + contacts + "]";
    } 

     
    
     public static class Builder {
        private User user;

        public Builder() {
            user = new User();  // Initialize the User instance

            List<Folder> defaultFolders = new ArrayList<>();
            defaultFolders.add(new Folder.Builder().setFolderName("Inbox").build());
            defaultFolders.add(new Folder.Builder().setFolderName("Sent").build());
            defaultFolders.add(new Folder.Builder().setFolderName("Draft").build());
            defaultFolders.add(new Folder.Builder().setFolderName("Trash").build());
            user.setFolders(defaultFolders);
        }

        // Setter methods for each field
        public Builder setUsername(String username) {
            user.setUsername(username);
            return this;
        }

        public Builder setPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public Builder setEmailAddress(String emailAddress) {
            user.setEmailAddress(emailAddress);
            return this;
        }

        public Builder setFolders(List<Folder> folders) {
            user.setFolders(folders);
            return this;
        }

        public Builder setContacts(List<Contact> contacts) {
            user.setContacts(contacts);
            return this;
        }


        // Build the User object
        public User build() { 
            return user;  // Return the constructed User object
        }
    }

}