package emailBackend.example.backend.validation;

import emailBackend.example.backend.classes.User;

public class UserContext {
    private String userName;
    private String email;
    private String password;
    private User user;

    // Getters and setters for userName, email, password, and user
    
    public User getUser() {
        return user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
