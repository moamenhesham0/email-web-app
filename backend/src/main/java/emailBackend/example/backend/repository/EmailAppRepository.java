package emailBackend.example.backend.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import emailBackend.example.backend.classes.User;

@Repository
public class EmailAppRepository {

    private static final String FILE_PATH = "usersInSystem.json"; 
    private Map<String, User> usersInSystem;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public EmailAppRepository() {
        usersInSystem = loadUsersFromFile();
    }


    public User getUserByEmail(String emailAddress){
        if (usersInSystem.containsKey(emailAddress)) {
            User user = usersInSystem.get(emailAddress);
                System.out.println(user);
                return user;
    
        } else {
            throw new IllegalStateException("No user found with the provided email.");
        }
    }
    
    public User checkUserExist(String emailAddress, String password) {
        if (usersInSystem.containsKey(emailAddress)) {
            User user = usersInSystem.get(emailAddress);
            if (user.getPassword().equals(password)) { 
                System.out.println(user);
                return user;
            } else {
                throw new IllegalStateException("Incorrect password. Please try again.");
            }
        } else {
            throw new IllegalStateException("No user found with the provided email.");
        }
    }

    public boolean signupCheck(String emailAddress){
        if (usersInSystem.containsKey(emailAddress)) {
            return true;
        }
        return false;
    }

    public void saveUserInSystem(User user) {
        if (usersInSystem.containsKey(user.getEmailAddress())) {
            System.out.println("User exists. Updating user details...");
        } else {
            System.out.println("Adding new user...");
        }
        // Add or update the user
        usersInSystem.put(user.getEmailAddress(), user);
        saveUsersToFile();
    }
    
    private void saveUsersToFile() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), usersInSystem);
            System.err.println("Successfully saved users to file: " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error saving users to file: " + e.getMessage());
        }
    }

    private Map<String, User> loadUsersFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.err.println("File not found. Initializing empty user map.");
            return new HashMap<>();
        }
        try {
            return objectMapper.readValue(file, new TypeReference<Map<String, User>>() {});
        } catch (IOException e) {
            System.err.println("Error loading users from file: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
