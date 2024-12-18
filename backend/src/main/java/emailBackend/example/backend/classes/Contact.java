package emailBackend.example.backend.classes;

import java.util.List;

public class Contact {
    String userName;
    List<String> emailAdress;
    
   
    public List<String> getEmailAdress() {
        return emailAdress;
    }
    public void setEmailAdress(List<String> emailAdress) {
        this.emailAdress = emailAdress;
    }

    @Override
    public String toString() {
        return "Contact [userNmae=" + userName + ", emailAdress=" + emailAdress + "]";
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    
}
