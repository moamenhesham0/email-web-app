package emailBackend.example.backend.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import emailBackend.example.backend.classes.Attachment;
import emailBackend.example.backend.service.UserService;




@RestController
@RequestMapping("/api/emails")
public class UserController {

     @Autowired
    private UserService userService;

    @GetMapping("/getEmail/{folderName}/{id}")
    public String readEmail(@PathVariable String folderName,@PathVariable String id) {
        System.out.println(userService.getEmailById(folderName, id).toString());
        return userService.getEmailById(folderName,id).toString();
    }
    
    @PostMapping("/createEmail")
        public void createEmail(
            @RequestParam("senderEmail") String senderEmail,
            @RequestParam("recipientEmail") String recipientEmail,
            @RequestParam("subject") String subject,
            @RequestParam("textBody") String textBody,
            @RequestParam("sendTheEmail") boolean sendTheEmail,
            @RequestParam("attachments") MultipartFile[] attachments
        ) {
            List<Attachment> attachmentList = new ArrayList<>();
            for (MultipartFile file : attachments) {
                try {
                    Attachment attachment = new Attachment();
                    attachment.setAttachment(file.getBytes());
                    attachment.setAttName(file.getOriginalFilename());
                    attachment.setAttType(file.getContentType());
                    attachment.setAttSize(file.getSize() / 1024.0f); // Size in KB
                    attachmentList.add(attachment);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to process attachment: " + file.getOriginalFilename(), e);
                }
            }

            userService.createEmail(senderEmail, recipientEmail, subject, textBody, attachmentList, sendTheEmail);
        }



    // @PostMapping("/creat/{senderEmail}/{recipientEmail}/{subject}/{textBody}")  /// sendTheEemail is boolean in the back if you eixt with out sending,  send the request with false
    // public void createEmail(@PathVariable String senderEmail,@PathVariable String recipientEmail, @PathVariable String subject, @PathVariable String textBody ){
    //      boolean sendTheEmail = true;
    //     List<Attachment> attachments = null;
    //     userService.createEmail( senderEmail, recipientEmail,  subject,  textBody, attachments, sendTheEmail);
    // }



    // @PostMapping("/send/{folderName}/{id}/{recipientEmailAddress}")
    // public void sendEmail(@PathVariable String folderName,@PathVariable String id, @PathVariable String recipientEmailAddress){
    //     Optional<Email> email = Optional.ofNullable(new Email());
        

    // }
    @DeleteMapping("/deleteEmail/{folder}/{id}")
    public void deleteEmail(@PathVariable String folder,@PathVariable String id){
        userService.deleteEmailById(folder, id);
    }


    @PostMapping("/addFolder/{folderName}")
    public void addFolder(@PathVariable String folderName) {

        userService.makeFolder(folderName);
        
    }

    @DeleteMapping("/deleteFolder/{folderName}")
    public void deleteFolder(@PathVariable String folderName){
        userService.deleteFolderByName(folderName);
    }

    @PostMapping("/moveEmails/{listOfIds}/{folderNameFrom}/{folderNameTo}")
    public void changeEmailsFolder(@PathVariable List<String> listOfIds, @PathVariable String folderNameFrom, @PathVariable String folderNameTo) {
       
        userService.moveEmails(listOfIds, folderNameFrom, folderNameTo);
    
    }

    @PostMapping("addContact/{userName}/{emailAddress}")
    public void addContact(@PathVariable String userName, @PathVariable String emailAddress ) {
       
       userService.addContact(userName, emailAddress ); 
    }

    @GetMapping("getContact/{emailAddress}")
    public String getMethodName(@PathVariable String emailAddress) {
     
        return userService.getContactByEmail(emailAddress).toString();
    }
    
    
    @DeleteMapping("deleteContact/{emailAddress}")
    public void deleteContact(@PathVariable String emailAddress){
        userService.removeContactFromList(emailAddress);
    }
    
    
}
