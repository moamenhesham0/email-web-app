package emailBackend.example.backend.service;


import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import emailBackend.example.backend.classes.Attachment;
import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.classes.Priority;
import emailBackend.example.backend.classes.User;
import emailBackend.example.backend.factory.EmailFactory;
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

     

    
    public Email createEmail(User profile,String senderEmail,String recipientEmail, String subject, String textBody, List<Attachment> attachments, boolean sendTheEmail, String priority) {
        
       
        
        Priority priorityGet  = new Priority(priority);
        Email email = emailFactory.createEmail(senderEmail, recipientEmail,subject ,textBody, attachments);
        if (!sendTheEmail) {
            System.out.println("i set prio");
            email.setPriority(priorityGet);
            profile.getFolders().get(2).getEmails().add(email);
            System.out.println(profile.getFolders().get(2).toString());
        }else{

            User user = emailAppRepository.getUserByEmail(recipientEmail);
            if (user != null) {
                System.out.println("i no set");
                user.getFolders().get(0).getEmails().add(email);
                emailAppRepository.saveUserInSystem(user);
                
                profile.getFolders().get(1).getEmails().add(email);
                profile.getFolders().get(1).getEmails().getLast().setPriority(priorityGet);
                System.out.println(profile.getFolders().get(1).toString());
            }
           
        }

       

        emailAppRepository.saveUserInSystem(profile);

        return email;
    }


    public List<String> sendToMulUser(User profile,String senderEmail,List<String> recipientsEmail, String subject, String textBody, List<Attachment> attachments, boolean sendTheEmail, String priority){
        
        List<String> ids = new ArrayList<>();
     
        for (String recipientEmail : recipientsEmail) {
        
        Email email = createEmail(profile, senderEmail, recipientEmail, subject, textBody, attachments, sendTheEmail, priority);

        ids.add(email.getId());
        }

        return ids;
    }

    public List<Email> getAllEmails(User profile, String folderName){
        int indexOfFolder = repositories.getFolderByName(profile,folderName);
        
        return profile.getFolders().get(indexOfFolder).getEmails();
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

            createEmail(profile,email.getSender(),email.getRecipient(), email.getTextBody(), email.getTextBody(), email.getAttachments(), true, null);
        }       
        emailAppRepository.saveUserInSystem(profile);
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


    public List<Attachment> getAttachments(User profile, String folderName, String id) {
        int indexOfFolder = repositories.getFolderByName(profile,folderName);
        int indexOfEmail = repositories.getEmailById(profile,folderName, id);

        return profile.getFolders().get(indexOfFolder).getEmails().get(indexOfEmail).getAttachments();
    }


    public void setPriority(User profile, String folderName, String id, String priority) {
        int indexOfFolder = repositories.getFolderByName(profile,folderName);
        int indexOfEmail = repositories.getEmailById(profile,folderName, id);
        
        Priority priorityGet  = new Priority(priority);
        profile.getFolders().get(indexOfFolder).getEmails().get(indexOfEmail).setPriority(priorityGet);

        emailAppRepository.saveUserInSystem(profile);
    }







    
}
