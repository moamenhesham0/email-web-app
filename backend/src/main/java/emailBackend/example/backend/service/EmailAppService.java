package emailBackend.example.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import emailBackend.example.backend.classes.User;
import emailBackend.example.backend.repository.EmailAppRepository;
import emailBackend.example.backend.validation.DefaultFoldersHandler;
import emailBackend.example.backend.validation.EmailCheckHandler;
import emailBackend.example.backend.validation.SaveUserHandler;
import emailBackend.example.backend.validation.UserContext;
import emailBackend.example.backend.validation.UserHandler;


@Service
public class EmailAppService {

    
    @Autowired
    EmailAppRepository emailAppRepository;

    
    

    public void checkLogin(String email, String password) {
        
        User user = emailAppRepository.checkUserExist(email, password);


        System.out.println("User " + user.getUsername() + " signed in successfully.");

    }

    public void saveUser(String userName, String email, String password) {

        UserHandler emailCheckHandler = new EmailCheckHandler(emailAppRepository);
        UserHandler defaultFoldersHandler = new DefaultFoldersHandler();
        UserHandler saveUserHandler = new SaveUserHandler(emailAppRepository);

        emailCheckHandler.setNext(defaultFoldersHandler);
        defaultFoldersHandler.setNext(saveUserHandler);

        // Create the context
        UserContext context = new UserContext();
        context.setUserName(userName);
        context.setEmail(email);
        context.setPassword(password);

        // Start the chain
        emailCheckHandler.handle(context);
    }

    public void signUserOut() {

    }

}
