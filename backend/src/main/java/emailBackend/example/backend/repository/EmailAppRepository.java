package emailBackend.example.backend.repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import emailBackend.example.backend.classes.User;

public class EmailAppRepository {

    private static final String FILE_PATH = "usersInSystem.ser"; 
    private Map<String, User> usersInSystem;

    public EmailAppRepository() {
        usersInSystem = loadUsersFromFile();
    }

    public User checkUserExist(String emailAddress, String password) {
        if (usersInSystem.containsKey(emailAddress)) {
            if (usersInSystem.get(emailAddress).getPassword().equals(password)) {
                System.out.println(usersInSystem.get(emailAddress).toString());
                return usersInSystem.get(emailAddress);
            } else {
                throw new IllegalStateException("Password is wrong, try again");
            }
        } else {
            throw new IllegalStateException("There is no user with this email");
        }
    }

    public void saveUserInSystem(User user) {
        if (usersInSystem.containsKey(user.getEmailAddress())) {
            throw new IllegalStateException("Email is taken");
        }
        System.out.println(user.toString());
        usersInSystem.put(user.getEmailAddress(), user);

        saveUsersToFile();
    }

    private void saveUsersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(usersInSystem);
            System.err.println("succsefuly saving users to file: " );
        } catch (IOException e) {
            System.err.println("Error saving users to file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, User> loadUsersFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            System.err.println("succsefuly loading users to file: " );
            return (Map<String, User>) ois.readObject();
        } catch (FileNotFoundException e) {
            // If the file doesn't exist, return an empty map
            System.err.println("succsefuly loading users to file: " );
            return new HashMap<>();
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading users from file: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
