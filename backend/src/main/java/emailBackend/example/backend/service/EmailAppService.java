package emailBackend.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import emailBackend.example.backend.classes.User;
import emailBackend.example.backend.factory.UserFactory;
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
        User user = UserFactory.createUser(userName, password, email);
       emailAppRepository.saveUserInSystem(user);
    }

    public void signUserOut() {

    }

}
