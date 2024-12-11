package emailBackend.example.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emailBackend.example.backend.classes.Profile;
import emailBackend.example.backend.classes.User;
import emailBackend.example.backend.service.EmailAppService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("api/service")
public class EmailAppController {
    
    EmailAppService emailAppService;

    @PostMapping("/signin/{email}/{password}")
    public void signin(@PathVariable String email, @PathVariable String password) {
      
        emailAppService.checkLogin(email, password);

    
    }

    @PostMapping("/signup/{userName}/{email}/{password}")
    public void signup(@PathVariable String userName, @PathVariable String email, @PathVariable String password) {
        
        
        emailAppService.saveUser(userName, email, password);
    }
    
}
