package emailBackend.example.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emailBackend.example.backend.service.EmailAppService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("api/service")
public class EmailAppController {
    
    @Autowired
    EmailAppService emailAppService;

    @PostMapping("/signin/{email}/{password}")
    public void signin(@PathVariable String email, @PathVariable String password) {
      
        emailAppService.checkLogin(email, password);

    
    }

    @PostMapping("/signup/{userName}/{email}/{password}")
    public void signup(@PathVariable String userName, @PathVariable String email, @PathVariable String password) {
        
        
        emailAppService.saveUser(userName, email, password);
    }

    @PostMapping("/signout")
    public void signout() {
        emailAppService.signUserOut();
    }
    
    
}
