package emailBackend.example.backend.repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.stereotype.Repository;

import emailBackend.example.backend.classes.Email;

@Repository
public class UserRepository {

    private static final String FILE_PATH = "emails.ser"; // File to store emails
    private final Map<String, Email> mails;

    public UserRepository() {
        // Load emails from file when the repository is initialized
        this.mails = loadEmailsFromFile();
    }

    // Save an email
    public void save(Email email) {
        mails.put(email.getId(), email);
        saveEmailsToFile(); // Save updated data to file
        System.out.println(email.toString());
    }

    // Find email by ID
    public Optional<Email> findById(String id) {
        return Optional.ofNullable(mails.get(id));
    }

    // Get all emails
    public List<Email> findAll() {
        return new ArrayList<>(mails.values());
    }

    
    public void deleteEmailById(String id) {
        if (mails.containsKey(id)) {
            mails.remove(id);
            saveEmailsToFile(); 
            System.out.println("Email with ID " + id + " deleted successfully.");
        } else {
            throw new IllegalStateException("There is no Email with this Id");
        }
    }

    // Save the emails map to a file
    private void saveEmailsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(mails);
        } catch (IOException e) {
            System.err.println("Error saving emails to file: " + e.getMessage());
        }
    }

    // Load the emails map from a file
    @SuppressWarnings("unchecked")
    private Map<String, Email> loadEmailsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (Map<String, Email>) ois.readObject();
        } catch (FileNotFoundException e) {
            // If the file doesn't exist, return an empty map
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading emails from file: " + e.getMessage());
            return new HashMap<>();
        }
    }

}
