package emailBackend.example.backend.repository;


import emailBackend.example.backend.classes.Folder;
import emailBackend.example.backend.classes.Profile;

public class FolderRepository {


    public Folder getFolderByName(String folderName){
        Profile profile = Profile.getInstance();

        if (profile.isSignin()) {
            throw new IllegalStateException("Another user is already signed in.");
        }
        
        int indexOfFolder ;
        int sizeOfFolder = profile.getUser().getFolders().size();

        for ( indexOfFolder= 0; indexOfFolder < sizeOfFolder; indexOfFolder++) {
            if (profile.getUser().getFolders().get(indexOfFolder).getFolderName().equals(folderName)) {
                return profile.getUser().getFolders().get(indexOfFolder);
            }
            
        }

            throw new IllegalStateException("No Folder Found With this name");

        
    }


}
