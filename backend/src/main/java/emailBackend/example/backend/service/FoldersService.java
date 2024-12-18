package emailBackend.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.classes.Folder;
import emailBackend.example.backend.classes.User;
import emailBackend.example.backend.factory.FolderFactory;
import emailBackend.example.backend.repository.EmailAppRepository;
import emailBackend.example.backend.repository.Repositories;

@Service
public class FoldersService {


    @Autowired
     private EmailAppRepository emailAppRepository;

    @Autowired
    private Repositories repositories;

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
