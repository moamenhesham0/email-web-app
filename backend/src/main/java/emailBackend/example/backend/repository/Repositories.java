package emailBackend.example.backend.repository;


import org.springframework.stereotype.Repository;


import emailBackend.example.backend.classes.Profile;

@Repository
public class Repositories {
    

    public int getFolderByName(String folderName){
        Profile profile = Profile.getInstance();

      
        
        int indexOfFolder ;
        int sizeOfFolder = profile.getUser().getFolders().size();

        for ( indexOfFolder= 0; indexOfFolder < sizeOfFolder; indexOfFolder++) {
            if (profile.getUser().getFolders().get(indexOfFolder).getFolderName().equals(folderName)) {
                return indexOfFolder;
            }
            
        }

            throw new IllegalStateException("No Folder Found With this name");

        
    }


    public int getEmailById(String folderName, String id){
        
        Profile profile = Profile.getInstance();
        
        int indexOfFolder ;
        int sizeOfFolder = profile.getUser().getFolders().size();
        int indexOfEmail;
        for ( indexOfFolder= 0; indexOfFolder < sizeOfFolder; indexOfFolder++) {
            if (profile.getUser().getFolders().get(indexOfFolder).getFolderName().equals(folderName)) {
                break;
            }
            
        }
        if (indexOfFolder == sizeOfFolder) {
            throw new IllegalStateException("No Folder Found With this name");
        }

        for ( indexOfEmail= 0; indexOfEmail < sizeOfFolder; indexOfEmail++) {
            if (profile.getUser().getFolders().get(indexOfFolder).getEmails().get(indexOfEmail).getId().equals(id)) {
                return indexOfEmail;
            }
            
        }
            
        throw new IllegalStateException("No Email Found With this id"); 
    }
}
