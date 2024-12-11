package emailBackend.example.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import emailBackend.example.backend.classes.Email;
import emailBackend.example.backend.service.FiltersService;

@RestController
@RequestMapping("/api/filters")
public class FiltersController {

    private final FiltersService filtersService;

    
    @Autowired
    public FiltersController(FiltersService filtersService) {
        this.filtersService = filtersService;
    }

    @PostMapping
    public List<Email> requestFilter(
            @RequestParam String method,
            @RequestBody List<Email> emails,
            @RequestParam String filterParameter) {
        method = method.toLowerCase();

        switch (method) {
            case "sender":
                return filtersService.filterBySender(emails, filterParameter);
            case "priority":
                return filtersService.filterByPriority(emails, filterParameter);
            case "attachment":
                return filtersService.filterByAttachment(emails, filterParameter);
            case "subject":
                return filtersService.filterBySubject(emails, filterParameter);
            default:
                throw new IllegalArgumentException("Invalid filtering method: " + method);
        }
    }

    // Separate endpoints for specific filters
    @GetMapping("/sender")
    public List<Email> filterBySender(
            @RequestBody List<Email> emails,
            @RequestParam String sender) {
        return filtersService.filterBySender(emails, sender);
    }

    @GetMapping("/attachment")
    public List<Email> filterByAttachment(
            @RequestBody List<Email> emails,
            @RequestParam String attachment) {
        return filtersService.filterByAttachment(emails, attachment);
    }

    @GetMapping("/priority")
    public List<Email> filterByPriority(
            @RequestBody List<Email> emails,
            @RequestParam String priority) {
        return filtersService.filterByPriority(emails, priority);
    }
    @GetMapping("/subject")
    public List<Email> filterBySubject(
            @RequestBody List<Email> emails,
            @RequestParam String subject) {
        return filtersService.filterByPriority(emails, subject);
    }
    
}

