package emailBackend.example.backend.controllers;

import emailBackend.example.backend.classes.Sort.SortStrategy;
import emailBackend.example.backend.classes.Sort.SortStrategyFactory;
import emailBackend.example.backend.repository.EmailAppRepository;
import emailBackend.example.backend.service.SortService;
import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.classes.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sort")
public class SortController {

    @Autowired
    private SortService sortService;

    @Autowired
    private SortStrategyFactory sortStrategyFactory;

    @Autowired
    EmailAppRepository emailAppRepository;

    @PostMapping("/emails")
    public List<Email> sortEmails(
            @RequestParam("emailAddress") String emailAddress,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("folderName") String folderName,
            @RequestParam("order") boolean order) {  ///if false reverse
        
                
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);        
        SortStrategy strategy = sortStrategyFactory.getStrategy(sortBy);

        List<Email> emails = sortService.sortEmails(loginUser, folderName, strategy);
        if (!order) {
            emails.reversed();
        }
        return emails;
    }
}
