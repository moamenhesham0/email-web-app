package emailBackend.example.backend.classes;

import java.io.Serializable;

public class Profile implements Serializable{

    private static Profile instance; 
    private User user;              
    private boolean isSignin = false; 

    
    private Profile() {}


    public static synchronized Profile getInstance() {
        if (instance == null) {
            instance = new Profile();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSignin() {
        return isSignin;
    }

    public void setSignin(boolean isSignin) {
        this.isSignin = isSignin;
    }

    // Method to sign out the current user
    public void signOut() {
        this.user = null;
        this.isSignin = false;
    }
}
