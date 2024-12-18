package emailBackend.example.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import emailBackend.example.backend.classes.Folder;
import emailBackend.example.backend.classes.User;
import emailBackend.example.backend.repository.EmailAppRepository;


@Service
public class EmailAppService {

    
    @Autowired
    EmailAppRepository emailAppRepository;

    
    

    public void checkLogin(String email, String password) {
        
        User user = emailAppRepository.checkUserExist(email, password);


        System.out.println("User " + user.getUsername() + " signed in successfully.");

    }

    public void saveUser(String userName, String email, String password) {

        if (emailAppRepository.signupCheck(email)) {
            throw new IllegalStateException("Email is taken");
        }

        List<Folder> defaultFolders = new ArrayList<>();
        defaultFolders.add(new Folder.Builder().setFolderName("Inbox").build());
        defaultFolders.add(new Folder.Builder().setFolderName("Sent").build());
        defaultFolders.add(new Folder.Builder().setFolderName("Draft").build());
        defaultFolders.add(new Folder.Builder().setFolderName("Trash").build());
        User user = new User.Builder()
        .setUsername(userName)
        .setEmailAddress(email)
        .setPassword(password)
        .setFolders(defaultFolders)
        .setContacts(new ArrayList<>())
        .build();

       emailAppRepository.saveUserInSystem(user);
    }

    public void signUserOut() {

    }

}
