package emailBackend.example.backend.service;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import emailBackend.example.backend.classes.Attachment;
import emailBackend.example.backend.classes.Contact;
import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.classes.Folder;
import emailBackend.example.backend.classes.Profile;
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

     

    
    public void createEmail(String senderEmail,String recipientEmail, String subject, String textBody, List<Attachment> attachments, boolean sendTheEmail) {
        
        Profile profile = Profile.getInstance();
        
        
        Email email = emailFactory.createEmail(senderEmail, recipientEmail,subject ,textBody, attachments);
        if (!sendTheEmail) {
            profile.getUser().getFolders().get(2).getEmails().add(email);
            System.out.println(profile.getUser().getFolders().get(2).toString());
        }else{

            User user = emailAppRepository.getUserByEmail(recipientEmail);
            user.getFolders().get(0).getEmails().add(email);
            emailAppRepository.saveUserInSystem(user);
        }

        profile.getUser().getFolders().get(1).getEmails().add(email);
            System.out.println(profile.getUser().getFolders().get(1).toString());

        emailAppRepository.saveUserInSystem(profile.getUser());
    }

    public Email getEmailById(String folderName,String id) {

        Profile profile = Profile.getInstance();

        
        int indexOfFolder = repositories.getFolderByName(folderName);
        int indexOfEmail = repositories.getEmailById(folderName, id);

        Email email = profile.getUser().getFolders().get(indexOfFolder).getEmails().get(indexOfEmail);
        email.setRead(true);
        emailAppRepository.saveUserInSystem(profile.getUser());
        return email;
        

    }

    public void deleteEmailById(String folderName,String id) {
       Profile profile = Profile.getInstance();

        int indexOfFolder = repositories.getFolderByName(folderName);
        int indexOfEmail = repositories.getEmailById(folderName, id);

       
        Email email = profile.getUser().getFolders().get(indexOfFolder).getEmails().remove(indexOfEmail);
        profile.getUser().getFolders().get(3).getEmails().add(email);

        emailAppRepository.saveUserInSystem(profile.getUser());
    }

    public void deleteFolderByName(String folderName) {
        Profile profile = Profile.getInstance();

        switch (folderName) {
            case "Inbox":
            case "Sent":
            case "Draft":
            case "Trash":
            case "Contact":
                throw new IllegalStateException("Cannot remove the default folder: " + folderName);
            default:
                
            int indexOfFolder = repositories.getFolderByName(folderName);
            try {
                for (Email email : profile.getUser().getFolders().get(indexOfFolder).getEmails()) {
                    profile.getUser().getFolders().get(3).getEmails().add(email);
                }
            } catch (Exception e) {
                System.out.println("no mails");
            }
            
            
            profile.getUser().getFolders().remove(indexOfFolder);

            emailAppRepository.saveUserInSystem(profile.getUser());
        }

    }

    public void makeFolder(String folderName) {

        Profile profile = Profile.getInstance();

        for (Folder  folder : profile.getUser().getFolders()) {
            if (folderName.equals(folder.getFolderName())) {
                throw new IllegalStateException("Folder Name already Exist: " + folderName);
            }
        }

        Folder newFolder = FolderFactory.creatFolder(folderName);

        profile.getUser().getFolders().add(newFolder);

        emailAppRepository.saveUserInSystem(profile.getUser());
    }

    public void moveEmails(List<String> listOfIds, String folderNameFrom, String folderNameTo) {
        Profile profile = Profile.getInstance();

        int indexOfFolderFrom = repositories.getFolderByName(folderNameFrom);
        int indexOfFolderTo = repositories.getFolderByName(folderNameTo);

        for (String id : listOfIds) {
            try {
                int indexOfEmail = repositories.getEmailById(folderNameFrom, id);
                Email email = profile.getUser().getFolders().get(indexOfFolderFrom).getEmails().get(indexOfEmail);

                profile.getUser().getFolders().get(indexOfFolderTo).getEmails().add(email); 
            } catch (Exception e) {
               System.err.println("The id "+id+" doesnt exist in the folder" );
            }
            
        }
        emailAppRepository.saveUserInSystem(profile.getUser());
    }

    public void addContact(String userName, String emailAddress) {
        
        Profile profile = Profile.getInstance();

        for (Contact con : profile.getUser().getContacts()) {
            if (con.getEmailAdress().equals(emailAddress) ) {
                System.out.println("email Adress is in your contact");
                System.out.println("do you want to change the name");
                throw new IllegalStateException("email address: "+emailAddress+" is in your contacts list ");
            }

        }

        Contact contact = new Contact();
        contact.setEmailAdress(emailAddress);
        contact.setUserNmae(userName);

        profile.getUser().getContacts().add(contact);
        emailAppRepository.saveUserInSystem(profile.getUser());
    }

    public Contact getContactByEmail(String emailAddress) {

        Profile profile = Profile.getInstance();

        for (Contact con : profile.getUser().getContacts()) {
            if (con.getEmailAdress().equals(emailAddress) ) {
                return con;
                
            }

        }

        throw new IllegalStateException("email address: "+emailAddress+" is not in your contacts list ");
    }

    public void removeContactFromList(String emailAddress) {
        
        Profile profile = Profile.getInstance();
        
        for (int i = 0; i < profile.getUser().getContacts().size(); i++) {
            if (profile.getUser().getContacts().get(i).getEmailAdress().equals(emailAddress)) {
                profile.getUser().getContacts().remove(i);
                emailAppRepository.saveUserInSystem(profile.getUser());
                return;
            }
        }

        throw new IllegalStateException("email address: "+emailAddress+" is not in your contacts list ");
    }
    
}
