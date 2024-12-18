package emailBackend.example.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import emailBackend.example.backend.classes.Contact;
import emailBackend.example.backend.classes.User;
import emailBackend.example.backend.repository.EmailAppRepository;

@Service
public class ContactService {
     @Autowired
     private EmailAppRepository emailAppRepository;



    
    public void addContact(User profile,String userName, List<String> emailAddresses) {
        

        for (Contact con : profile.getContacts()) {
            if (con.getUserName().equals(userName)) {
                System.out.println("user Name is in your contact");
                throw new IllegalStateException("User name: "+userName+" is in your contacts list ");
            }

        }

        Contact contact = new Contact();
        contact.setEmailAdress(emailAddresses);
        contact.setUserName(userName);

        profile.getContacts().add(contact);
        emailAppRepository.saveUserInSystem(profile);
    }


    public List<String> getContactByUsername(User profile, String userName) {
        
        for (Contact con : profile.getContacts()) {
            if (con.getUserName().equals(userName)) {
                return con.getEmailAdress();
            }
        }
        throw new IllegalStateException("user Name does not exist");
    }


    public void deleteContactByUserName(User profile, String userName) {
       
        for (Contact con : profile.getContacts()) {
            if (con.getUserName().equals(userName)) {
                profile.getContacts().remove(con);  ///////////////////////////////////////////// want to be tested
                emailAppRepository.saveUserInSystem(profile);
                return;
            }
        }
        throw new IllegalStateException("user Name does not exist");
    }


    
    public void deleteContactEmailAddress(User profile, String userName, String contactEmailAddress) {
        
        for (Contact con : profile.getContacts()) {
            if (con.getUserName().equals(userName)) {
                    for (String em : con.getEmailAdress()) {
                        if (em.equals(contactEmailAddress)) {
                            con.getEmailAdress().remove(contactEmailAddress);   ///////////////////////////////////////////// want to be tested
                            emailAppRepository.saveUserInSystem(profile);
                            return;
                        }
                    }  

                    throw new IllegalStateException("Email Address does not exist for this contact userName");
            }
        }

        throw new IllegalStateException("User name does not exist for this contact");
        
    }


    public void addEmailToContact(User profile, String userName, String newEmailAddress) {
        List<String> emailAddresses = getContactByUsername(profile, userName);
        emailAddresses.add(userName);
        emailAppRepository.saveUserInSystem(profile);
    }
    public List<Contact> searchContacts(User profile, String searchQuery) {
        return profile.getContacts().stream()
                .filter(contact -> 
                        contact.getUserName().toLowerCase().contains(searchQuery.toLowerCase()) || 
                        contact.getEmailAdress().stream()
                            .anyMatch(email -> email.toLowerCase().contains(searchQuery.toLowerCase())))
                .collect(Collectors.toList());
    }

    // Sort contacts by userName in ascending order
    public List<Contact> sortContactsByName(List<Contact> contacts) {
        return contacts.stream()
                .sorted((c1, c2) -> c1.getUserName().compareToIgnoreCase(c2.getUserName()))
                .collect(Collectors.toList());
    }
}
