package emailBackend.example.backend.factory;

import java.util.ArrayList;
import java.util.List;

import emailBackend.example.backend.classes.Folder;
import emailBackend.example.backend.classes.User;

public class UserFactory {

    public static User createUser(String username, String password, String emailAddress) {
 
        List<Folder> defaultFolders = new ArrayList<>();
        defaultFolders.add(FolderFactory.creatFolder("Inbox"));
        defaultFolders.add(FolderFactory.creatFolder("Sent"));
        defaultFolders.add(FolderFactory.creatFolder("Draft"));
        defaultFolders.add(FolderFactory.creatFolder("Trash"));



        return new User(username, password, emailAddress, defaultFolders, new ArrayList<>());
    }

}