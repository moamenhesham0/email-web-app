package emailBackend.example.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emailBackend.example.backend.classes.Attachment;
import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.classes.Folder;
import emailBackend.example.backend.factory.FolderFactory;
import emailBackend.example.backend.service.UserService;



@RestController
@RequestMapping("/api/emails")
public class UserController {

     @Autowired
    private UserService userService;

    @GetMapping("/get/{id}")
    public Optional<Email> readEmail(@PathVariable String id) {
        System.out.println(userService.getEmailById(id).toString());
        return userService.getEmailById(id);
    }
    @PostMapping("/creat/{senderEmail}/{recipientEmail}/{subject}/{textBody}/{attachments}")
    public void createEmail(@PathVariable String senderEmail,@PathVariable String recipientEmail, @PathVariable String subject, @PathVariable String textBody, @PathVariable List<Attachment> attachments){
        
        userService.createEmail( senderEmail, recipientEmail,  subject,  textBody, attachments);
    }

    @PostMapping("/send/{id}")
    public void sendEmail(@PathVariable String id){
        Optional<Email> email = Optional.ofNullable(new Email());
        email = userService.getEmailById(id);

    }
    @DeleteMapping("/deleteEmail/{id}")
    public void deleteEmail(@PathVariable String id){
        userService.deleteEmailById(id);
    }


    @PostMapping("/addFolder/{folderName}")
    public Folder addFolder(@PathVariable String folderName) {
        Folder newFolder = FolderFactory.creatFolder(folderName);
        return newFolder;
    }

    @DeleteMapping("/deleteFolder/{folderName}")
    public void deleteFolder(@PathVariable String id){
        userService.deleteFolderByName(id);
    }
    
}
