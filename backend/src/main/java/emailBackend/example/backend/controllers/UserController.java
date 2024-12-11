package emailBackend.example.backend.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.service.UserService;



@RestController
@RequestMapping("/api/emails")
public class UserController {

     @Autowired
    private UserService userService;

    @GetMapping("/get/{id}")
    public Optional<Email> getEmail(@PathVariable String id) {
        System.out.println(userService.getEmailById(id).toString());
        return userService.getEmailById(id);
    }
    @PostMapping("/send/{email}/{subject}/{textBody}")
    public void sendEmail(@PathVariable String email, @PathVariable String subject, @PathVariable String textBody){
        Email makeEmail = new Email();
        makeEmail.setRecipient(email);
        makeEmail.setSubject(subject);
        makeEmail.setTextBody(textBody);
        makeEmail.setAttachments(null);
        userService.createEmail(makeEmail);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteEmail(@PathVariable String id){
        userService.deleteEmailById(id);
    }


    @PostMapping("/{folderName}")
    public String addFolder(@PathVariable String folderName) {
        
        return folderName;
    }
    
}
