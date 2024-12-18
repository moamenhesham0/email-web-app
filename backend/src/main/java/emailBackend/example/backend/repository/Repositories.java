package emailBackend.example.backend.repository;


import org.springframework.stereotype.Repository;

import emailBackend.example.backend.classes.User;

@Repository
public class Repositories {
    

    public int getFolderByName(User profile,String folderName){

        
        int indexOfFolder ;
        int sizeOfFolder = profile.getFolders().size();

        for ( indexOfFolder= 0; indexOfFolder < sizeOfFolder; indexOfFolder++) {
            if (profile.getFolders().get(indexOfFolder).getFolderName().equals(folderName)) {
                return indexOfFolder;
            }
            
        }

            throw new IllegalStateException("No Folder Found With this name");

        
    }


    public int getEmailById(User profile,String folderName, String id){
        

        
        int indexOfFolder = getFolderByName(profile, folderName);
        int indexOfEmail;

        int sizeOfEmails = profile.getFolders().get(indexOfFolder).getEmails().size();

        for ( indexOfEmail= 0; indexOfEmail < sizeOfEmails; indexOfEmail++) {
            if (profile.getFolders().get(indexOfFolder).getEmails().get(indexOfEmail).getId().equals(id)) {
                return indexOfEmail;
            }
            
        }
            
        throw new IllegalStateException("No Email Found With this id"); 
    }
}
