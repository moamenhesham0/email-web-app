package emailBackend.example.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emailBackend.example.backend.classes.User;
import emailBackend.example.backend.service.EmailAppService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("api/service")
public class EmailAppController {
    
    EmailAppService emailAppService = new EmailAppService();

    @PostMapping("/signin/{email}/{password}")
    public User login(@PathVariable String email, @PathVariable String password) {
        
        return emailAppService.checkLogin(email, password);
       

    }

    @PostMapping("/signup/{userName}/{email}/{password}")
    public void signup(@PathVariable String userName, @PathVariable String email, @PathVariable String password) {
        User user = new User();
        user.setUsername(userName);
        user.setEmailAddress(email);
        user.setPassword(password);
        user.setFolders(null);  ///will change later
        
        emailAppService.saveUser(user);
    }
    
}
