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
import emailBackend.example.backend.classes.User;
import emailBackend.example.backend.repository.EmailAppRepository;
import emailBackend.example.backend.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;






@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    EmailAppRepository emailAppRepository;
    

    @GetMapping("/getEmail/{folderName}/{id}")
    public String readEmail(@RequestParam("emailAddress") String emailAddress,@PathVariable String folderName,@PathVariable String id) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        System.out.println(userService.getEmailById(loginUser, folderName, id).toString());
        return userService.getEmailById(loginUser,folderName,id).toString();
    }
    
    @PostMapping("/createEmail")
        public List<String> createEmail(
            @RequestParam("senderEmail") String senderEmail,
            @RequestParam("recipientEmail") List<String> recipientEmail,
            @RequestParam("subject") String subject,
            @RequestParam("textBody") String textBody,
            @RequestParam("sendTheEmail") boolean sendTheEmail,
            @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
        ) {

            User loginUser = emailAppRepository.getUserByEmail(senderEmail);
            List<Attachment> attachmentList = new ArrayList<>();
            if (attachments != null && attachments.length > 0) {
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
            }

            return userService.sendToMulUser(loginUser,senderEmail, recipientEmail, subject, textBody, attachmentList, sendTheEmail); //return the id of the email save it
        }

        


        @PostMapping("/sendEmailById")    ///for draft specially
        public void sendEmailById(
            @RequestParam("emailAddress") String emailAddress,
            @RequestParam("folderName") String folderName,
            @RequestParam("id") String id

        ) {
            User loginUser = emailAppRepository.getUserByEmail(emailAddress);
            userService.sendEmailById(loginUser, folderName, id);

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


    @DeleteMapping("/deleteEmail")
    public void deleteEmail(@RequestParam("emailAddress") String emailAddress, @RequestParam("folderName") String folderName,@RequestParam("ids") List<String> ids){
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        userService.deleteEmailById(loginUser,folderName, ids);
    }


    @PostMapping("/addFolder/{folderName}")
    public void addFolder(@RequestParam("emailAddress") String emailAddress,@PathVariable String folderName) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        userService.makeFolder(loginUser,folderName);
        
    }

    @DeleteMapping("/deleteFolder/{folderName}")
    public void deleteFolder(@RequestParam("emailAddress") String emailAddress,@PathVariable String folderName){
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        userService.deleteFolderByName(loginUser,folderName);
    }

    @PostMapping("/renameFolder/{folderName}/{folderNewName}")
    public void renameFolder(@RequestParam("emailAddress") String emailAddress,@PathVariable String folderName, @PathVariable String folderNewName) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        userService.renameFolder(loginUser,folderName, folderNewName);
    }
    

    @PostMapping("/moveEmails/{listOfIds}/{folderNameFrom}/{folderNameTo}")
    public void changeEmailsFolder(@RequestParam("emailAddress") String emailAddress,@PathVariable List<String> listOfIds, @PathVariable String folderNameFrom, @PathVariable String folderNameTo) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        userService.moveEmails(loginUser,listOfIds, folderNameFrom, folderNameTo);
    
    }

    @PostMapping("addContact")
    public void addContact(@RequestParam("emailAddress") String emailAddress,@RequestParam("userName") String userName, @RequestParam("emailAddressesContact") List<String> emailAddressesContact ) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
       userService.addContact(loginUser,userName, emailAddressesContact ); 
    }

    @GetMapping("getContact")
    public List<String> getContactByUsername(@RequestParam("emailAddress") String emailAddress,@RequestParam("userName") String userName) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        return userService.getContactByUsername(loginUser,userName);
    }
    
    
    @DeleteMapping("deleteContactByUserName")
    public void deleteContactByUserName(@RequestParam("emailAddress") String emailAddress,@RequestParam("userName") String userName ){
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        userService.deleteContactByUserName(loginUser,userName);
    }
    
    @PostMapping("deleteContactEmailAddress")
    public void deleteContactEmailAddress(@RequestParam("emailAddress") String emailAddress,@RequestParam("userName") String userName, @RequestParam("contactEmailAddress") String contactEmailAddress) {
        
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        userService.deleteContactEmailAddress(loginUser,userName, contactEmailAddress);
       
    }
    
    
}
