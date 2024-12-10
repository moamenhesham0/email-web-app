package emailBackend.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;

import emailBackend.example.backend.classes.User;
import emailBackend.example.backend.repository.EmailAppRepository;

public class EmailAppService {

    @Autowired
    EmailAppRepository emailAppRepository = new EmailAppRepository();

    public User checkLogin(String email, String password) {
        
        return emailAppRepository.checkUserExist(email, password);

    }

    public void saveUser(User user) {
       emailAppRepository.saveUserInSystem(user);
    }

}
