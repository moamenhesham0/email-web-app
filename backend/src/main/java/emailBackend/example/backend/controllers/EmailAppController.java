package emailBackend.example.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import emailBackend.example.backend.service.EmailAppService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("api/service")
public class EmailAppController {
    
    @Autowired
    EmailAppService emailAppService;

    @PostMapping("/signin")
    public void signin(@RequestParam("email") String email, @RequestParam("password") String password) {
      
        emailAppService.checkLogin(email, password);

    
    }

    @PostMapping("/signup")
    public void signup(@RequestParam("userName") String userName, @RequestParam("email") String email, @RequestParam("password") String password) {
    

        emailAppService.saveUser(userName, email, password);
    }

    @PostMapping("/logout")
    public void signout() {
        emailAppService.signUserOut();
    }
    
    
}
