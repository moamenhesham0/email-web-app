package emailBackend.example.backend.classes;

public class Contact {
    String userNmae;
    String emailAdress;
    
    public String getUserNmae() {
        return userNmae;
    }
    public void setUserNmae(String userNmae) {
        this.userNmae = userNmae;
    }
    public String getEmailAdress() {
        return emailAdress;
    }
    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }
    
    @Override
    public String toString() {
        return "Contact [userNmae=" + userNmae + ", emailAdress=" + emailAdress + "]";
    }

    
}
