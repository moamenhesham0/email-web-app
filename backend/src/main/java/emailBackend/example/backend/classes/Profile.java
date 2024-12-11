package emailBackend.example.backend.classes;

public class Profile {

    private static Profile instance; // Singleton instance
    private User user;               // Logged-in user
    private boolean isSignin = false; // Sign-in status

    // Private constructor to prevent direct instantiation
    private Profile() {}

    // Public method to get the singleton instance
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
