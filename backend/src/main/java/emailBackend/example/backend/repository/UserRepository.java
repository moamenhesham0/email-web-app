package emailBackend.example.backend.repository;

import java.io.*;
import java.util.*;

import org.springframework.stereotype.Repository;

import emailBackend.example.backend.classes.Email;

@Repository
public class UserRepository {


    

    private static final String FILE_PATH = "emails.ser"; 
    private final Map<String, Email> mails;

    public UserRepository() {
     
        this.mails = loadEmailsFromFile();
    }


    public void save(Email email) {
        mails.put(email.getId(), email);
        saveEmailsToFile(); 
        System.out.println(email.toString());
    }


    public Optional<Email> findById(String id) {
        return Optional.ofNullable(mails.get(id));
    }


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

    private void saveEmailsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(mails);
        } catch (IOException e) {
            System.err.println("Error saving emails to file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Email> loadEmailsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (Map<String, Email>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading emails from file: " + e.getMessage());
            return new HashMap<>();
        }
    }

}
