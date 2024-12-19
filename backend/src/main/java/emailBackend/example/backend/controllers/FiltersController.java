package emailBackend.example.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import emailBackend.example.backend.classes.Filters.*;
import emailBackend.example.backend.classes.*;
import emailBackend.example.backend.repository.EmailAppRepository;
import emailBackend.example.backend.interfaces.Filter;
import emailBackend.example.backend.service.FiltersService;

@RestController
@RequestMapping("/api/filters")
public class FiltersController {

    @Autowired
    private FiltersService filtersService;

    @Autowired
    private FilterFactory filterFactory;

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
        Filter filter  = filterFactory.getFilter(searchBy);

        // Perform the search
        return filtersService.filterEmails(user, folderName, filter, keyword);
    }
}
