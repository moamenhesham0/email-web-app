package emailBackend.example.backend.service;


import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import emailBackend.example.backend.classes.Attachment;
import emailBackend.example.backend.classes.Contact;
import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.classes.Folder;
import emailBackend.example.backend.classes.User;
import emailBackend.example.backend.factory.EmailFactory;
import emailBackend.example.backend.factory.FolderFactory;
import emailBackend.example.backend.repository.EmailAppRepository;
import emailBackend.example.backend.repository.Repositories;

@Service
public class UserService {
    
    @Autowired
    EmailAppRepository emailAppRepository;

    @Autowired
    private EmailFactory emailFactory;

    @Autowired
    private Repositories repositories;

     

    
    public Email createEmail(User profile,String senderEmail,String recipientEmail, String subject, String textBody, List<Attachment> attachments, boolean sendTheEmail) {
        
       
        
        
        Email email = emailFactory.createEmail(senderEmail, recipientEmail,subject ,textBody, attachments);
        if (!sendTheEmail) {
            profile.getFolders().get(2).getEmails().add(email);
            System.out.println(profile.getFolders().get(2).toString());
        }else{

            User user = emailAppRepository.getUserByEmail(recipientEmail);
            if (user != null) {
                user.getFolders().get(0).getEmails().add(email);
                emailAppRepository.saveUserInSystem(user);
                profile.getFolders().get(1).getEmails().add(email);
                System.out.println(profile.getFolders().get(1).toString());
            }
           
        }

       

        emailAppRepository.saveUserInSystem(profile);

        return email;
    }


    public List<String> sendToMulUser(User profile,String senderEmail,List<String> recipientsEmail, String subject, String textBody, List<Attachment> attachments, boolean sendTheEmail){
        
        List<String> ids = new ArrayList<>();
        for (String recipientEmail : recipientsEmail) {
            
        Email email = createEmail(profile, senderEmail, recipientEmail, subject, textBody, attachments, sendTheEmail);

        ids.add(email.getId());
        }

        return ids;
    }

    public Email getEmailById(User profile,String folderName,String id) {

        

        
        int indexOfFolder = repositories.getFolderByName(profile,folderName);
        int indexOfEmail = repositories.getEmailById(profile,folderName, id);

        Email email = profile.getFolders().get(indexOfFolder).getEmails().get(indexOfEmail);
        email.setRead(true);
        emailAppRepository.saveUserInSystem(profile);
        return email;
        

    }

    public List<Email> getMulEmails(User profile,String folderName,List<String> ids){

        List<Email> emails = new ArrayList<>();

        for (String id : ids) {
            Email getEmail = getEmailById(profile, folderName, id);
            emails.add(getEmail);
        }

        return emails;
    }

    public void sendEmailById(User profile,String folderName,List<String> ids){
        for (String id : ids) {
            Email email = getEmailById(profile, folderName, id);

            createEmail(profile,email.getSender(),email.getRecipient(), email.getTextBody(), email.getTextBody(), email.getAttachments(), true);
        }       
       
    }

    public void deleteEmailById(User profile,String folderName,List<String> ids) {
      
        int indexOfFolder = repositories.getFolderByName(profile,folderName);
        for (String id : ids) {
     
            int indexOfEmail = repositories.getEmailById(profile,folderName, id);

        
            Email email = profile.getFolders().get(indexOfFolder).getEmails().remove(indexOfEmail);

            if (!folderName.equals("Trash")) {
                profile.getFolders().get(3).getEmails().add(email);
            }
        
        }
        

        emailAppRepository.saveUserInSystem(profile);
    }

    public void deleteFolderByName(User profile,String folderName) {

        switch (folderName) {
            case "Inbox":
            case "Sent":
            case "Draft":
            case "Trash":
            case "Contact":
                throw new IllegalStateException("Cannot remove the default folder: " + folderName);
            default:
                
            int indexOfFolder = repositories.getFolderByName(profile,folderName);
            try {
                for (Email email : profile.getFolders().get(indexOfFolder).getEmails()) {
                    profile.getFolders().get(3).getEmails().add(email);
                }
            } catch (Exception e) {
                System.out.println("no mails");
            }
            
            
            profile.getFolders().remove(indexOfFolder);

            emailAppRepository.saveUserInSystem(profile);
        }

    }

    public void makeFolder(User profile,String folderName) {

        validateFolderName(folderName);
        for (Folder  folder : profile.getFolders()) {
            if (folderName.equals(folder.getFolderName())) {
                throw new IllegalStateException("Folder Name already Exist: " + folderName);
            }
        }

        Folder newFolder = FolderFactory.creatFolder(folderName);

        profile.getFolders().add(newFolder);

        emailAppRepository.saveUserInSystem(profile);
    }

    public void moveEmails(User profile,List<String> listOfIds, String folderNameFrom, String folderNameTo) {

        int indexOfFolderFrom = repositories.getFolderByName(profile,folderNameFrom);
        int indexOfFolderTo = repositories.getFolderByName(profile, folderNameTo);

        for (String id : listOfIds) {
            try {
                int indexOfEmail = repositories.getEmailById(profile, folderNameFrom, id);
                Email email = profile.getFolders().get(indexOfFolderFrom).getEmails().get(indexOfEmail);

                profile.getFolders().get(indexOfFolderTo).getEmails().add(email); 
            } catch (Exception e) {
               System.err.println("The id "+id+" doesnt exist in the folder" );
            }
            
        }
        emailAppRepository.saveUserInSystem(profile);
    }

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



    public void renameFolder(User profile, String folderName, String folderNewName) {

        validateFolderName(folderNewName);
        for (Folder  folder : profile.getFolders()) {
            if (folderNewName.equals(folder.getFolderName())) {
                throw new IllegalStateException("Folder Name already Exist: " + folderName);
            }
        }
                
        int indexOfFolder = repositories.getFolderByName(profile,folderName);
        profile.getFolders().get(indexOfFolder).setFolderName(folderNewName);
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


    private void validateFolderName(String folderName) {
        if (folderName == null || folderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Folder name cannot be null or empty.");
        }
    
        // Check for invalid characters or reserved names
        String[] reservedFolders = {"Inbox", "Sent", "Draft", "Trash", "Contact"};
        for (String reserved : reservedFolders) {
            if (reserved.equalsIgnoreCase(folderName)) {
                throw new IllegalStateException("Cannot use a reserved folder name: " + folderName);
            }
        }
    
        // Check for invalid characters (e.g., disallow special characters)
        if (!folderName.matches("^[a-zA-Z0-9_ ]+$")) {
            throw new IllegalArgumentException("Folder name contains invalid characters.");
        }
    
        // Enforce a maximum length
        if (folderName.length() > 50) {
            throw new IllegalArgumentException("Folder name cannot exceed 50 characters.");
        }
    }
    

    
}
