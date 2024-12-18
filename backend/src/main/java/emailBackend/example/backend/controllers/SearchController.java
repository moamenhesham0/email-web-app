package emailBackend.example.backend.controllers;

import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.classes.Search.SearchStrategy;
import emailBackend.example.backend.classes.Search.SearchStrategyFactory;
import emailBackend.example.backend.repository.EmailAppRepository;
import emailBackend.example.backend.service.SearchService;
import emailBackend.example.backend.classes.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private SearchStrategyFactory searchStrategyFactory;

    @Autowired
    private EmailAppRepository emailAppRepository;

    @PostMapping("/emails")
    public List<Email> searchEmails(
            @RequestParam("emailAddress") String emailAddress,
            @RequestParam("searchBy") String searchBy,
            @RequestParam("folderName") String folderName,
            @RequestParam("keyword") String keyword) {

        // Fetch the user
        User user = emailAppRepository.getUserByEmail(emailAddress);

        // Get the search strategy
        SearchStrategy strategy = searchStrategyFactory.getStrategy(searchBy);

        // Perform the search
        return searchService.searchEmails(user, folderName, strategy, keyword);
    }
}

