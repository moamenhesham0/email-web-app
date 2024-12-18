package emailBackend.example.backend.repository;


import emailBackend.example.backend.classes.Folder;
import emailBackend.example.backend.classes.User;

public class FolderRepository {


    public Folder getFolderByName(User profile,String folderName){
        
        int indexOfFolder ;
        int sizeOfFolder = profile.getFolders().size();

        for ( indexOfFolder= 0; indexOfFolder < sizeOfFolder; indexOfFolder++) {
            if (profile.getFolders().get(indexOfFolder).getFolderName().equals(folderName)) {
                return profile.getFolders().get(indexOfFolder);
            }
            
        }

            throw new IllegalStateException("No Folder Found With this name");

        
    }


}
