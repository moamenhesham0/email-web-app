package emailBackend.example.backend.controllers;

import emailBackend.example.backend.classes.Sort.SortStrategy;
import emailBackend.example.backend.classes.Sort.SortStrategyFactory;
import emailBackend.example.backend.service.SortService;
import emailBackend.example.backend.classes.Email;

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

    @PostMapping("/emails")
    public List<Email> sortEmails(
            @RequestParam("sortBy") String sortBy,
            @RequestBody List<Email> emails) {

        SortStrategy strategy = sortStrategyFactory.getStrategy(sortBy);
        return sortService.sortEmails(emails, strategy);
    }
}
