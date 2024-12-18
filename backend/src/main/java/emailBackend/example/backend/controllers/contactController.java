package emailBackend.example.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import emailBackend.example.backend.classes.Contact;
import emailBackend.example.backend.classes.User;
import emailBackend.example.backend.repository.EmailAppRepository;
import emailBackend.example.backend.service.ContactService;



@RestController
@RequestMapping("api/user")
public class contactController {


    @Autowired
    EmailAppRepository emailAppRepository;

    @Autowired
    private ContactService contactService;


    @PostMapping("/addContact")
    public void addContact(@RequestParam("emailAddress") String emailAddress,@RequestParam("userName") String userName, @RequestParam("emailAddressesContact") List<String> emailAddressesContact ) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        contactService.addContact(loginUser,userName, emailAddressesContact ); 
    }

    @PostMapping("/addEmailToContact")
    public void addEmailToContact(@RequestParam("emailAddress") String emailAddress, @RequestParam("userName") String userName , @RequestParam("newEmailAddress") String newEmailAddress) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
         contactService.addEmailToContact(loginUser, userName, newEmailAddress);       

    }
    

    @GetMapping("/getContacts")
    public List<Contact> getContact(@RequestParam("emailAddress") String emailAddress) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        return loginUser.getContacts();
    }
    
    @GetMapping("/getContactByUsername")
    public List<String> getContactByUsername(@RequestParam("emailAddress") String emailAddress,@RequestParam("userName") String userName) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        return contactService.getContactByUsername(loginUser,userName);
    }
    

    
    @DeleteMapping("/deleteContactByUserName")
    public void deleteContactByUserName(@RequestParam("emailAddress") String emailAddress,@RequestParam("userName") String userName ){
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        contactService.deleteContactByUserName(loginUser,userName);
    }
    
    @PostMapping("/deleteContactEmailAddress")
    public void deleteContactEmailAddress(@RequestParam("emailAddress") String emailAddress,@RequestParam("userName") String userName, @RequestParam("contactEmailAddress") String contactEmailAddress) {
        
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        contactService.deleteContactEmailAddress(loginUser,userName, contactEmailAddress);
       
    }
    @GetMapping("/searchContacts")
    public List<Contact> searchContacts(
            @RequestParam("emailAddress") String emailAddress,
            @RequestParam("query") String query) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        return contactService.searchContacts(loginUser, query);
    }

    // Sort contacts by userName
    @GetMapping("/sortContacts")
    public List<Contact> sortContacts(
            @RequestParam("emailAddress") String emailAddress) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        return contactService.sortContactsByName(loginUser.getContacts());
    }
}
