package emailBackend.example.backend.classes;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    String username;
    String password; 
    String emailAddress; 
    List<Folder> folders;
    
    public User() {}
    
    public User(String username, String password, String emailAddress, List<Folder> folders) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.folders = folders;
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
    
    
    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password + ", emailAddress=" + emailAddress + ", folders="
                + folders + "]";
    } 

        

}