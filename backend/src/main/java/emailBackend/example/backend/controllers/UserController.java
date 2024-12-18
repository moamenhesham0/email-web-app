package emailBackend.example.backend.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import emailBackend.example.backend.classes.Attachment;
import emailBackend.example.backend.classes.Email;
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
    

    @GetMapping("/getEmail")
    public List<Email> readEmail(@RequestParam("emailAddress") String emailAddress,@RequestParam("folderName") String folderName,@RequestParam("ids") List<String> ids) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        System.out.println(userService.getMulEmails(loginUser, folderName, ids).toString());
        return userService.getMulEmails(loginUser,folderName,ids);
    }

    @GetMapping("/getEmailByFolder")
    public List<Email> getEmailByFolder(@RequestParam("emailAddress") String emailAddress,@RequestParam("folderName") String folderName) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        System.out.println(userService.getAllEmails(loginUser, folderName ).toString());
        return userService.getAllEmails(loginUser,folderName);
    }

    @GetMapping("/getAttachment")
    public List<Attachment> getAttachment(@RequestParam("emailAddress") String emailAddress,@RequestParam("folderName") String folderName,@RequestParam("id") String id) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);

        return userService.getAttachments(loginUser, folderName, id);
       
    }
    
    
    
    @PostMapping("/createEmail")
        public List<String> createEmail(
            @RequestParam("senderEmail") String senderEmail,
            @RequestParam("recipientEmail") List<String> recipientEmail,
            @RequestParam("subject") String subject,
            @RequestParam("textBody") String textBody,
            @RequestParam("sendTheEmail") boolean sendTheEmail,
            @RequestParam("priority") String priority,///////
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

            return userService.sendToMulUser(loginUser,senderEmail, recipientEmail, subject, textBody, attachmentList, sendTheEmail, priority); //return the id of the email save it
        }

        


        @PostMapping("/sendEmailById")    ///for draft specially
        public void sendEmailById(
            @RequestParam("emailAddress") String emailAddress,
            @RequestParam("folderName") String folderName,
            @RequestParam("id") List<String> ids

        ) {
            User loginUser = emailAppRepository.getUserByEmail(emailAddress);
            userService.sendEmailById(loginUser, folderName, ids);

        }

    @DeleteMapping("/deleteEmail")
    public void deleteEmail(@RequestParam("emailAddress") String emailAddress, @RequestParam("folderName") String folderName,@RequestParam("ids") List<String> ids){
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        userService.deleteEmailById(loginUser,folderName, ids);
    }


    @PostMapping("/moveEmails")
    public void changeEmailsFolder(@RequestParam("emailAddress") String emailAddress,@RequestParam("listOfIds") List<String> listOfIds, @RequestParam("folderNameFrom") String folderNameFrom, @RequestParam("folderNameTo") String folderNameTo) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        userService.moveEmails(loginUser,listOfIds, folderNameFrom, folderNameTo);
    
    }


    @PostMapping("/setPriority")
    public void setPriority(@RequestParam("emailAddress") String emailAddress,@RequestParam("folderName") String folderName,@RequestParam("id") String id, @RequestParam("priority") String priority) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        userService.setPriority(loginUser, folderName, id, priority);
        
    }
    
    
}
