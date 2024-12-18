package emailBackend.example.backend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import emailBackend.example.backend.classes.Folder;
import emailBackend.example.backend.classes.User;
import emailBackend.example.backend.repository.EmailAppRepository;
import emailBackend.example.backend.service.FoldersService;


@RestController
@RequestMapping("api/user")
public class FolderController {


    @Autowired
    private EmailAppRepository emailAppRepository;

    @Autowired
    private FoldersService foldersService;

    @PostMapping("/addFolder")
    public void addFolder(@RequestParam("emailAddress") String emailAddress, @RequestParam("folderName") String folderName) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        foldersService.makeFolder(loginUser,folderName);
        
    }

    @GetMapping("/loadFolders")
    public List<String> loadFolders(@RequestParam("emailAddress") String emailAddress) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        List<String> foldersName = new ArrayList<>();
        for (Folder folder : loginUser.getFolders()) {
            foldersName.add(folder.getFolderName());
        }

        return foldersName;
    }
    

    @DeleteMapping("/deleteFolder")
    public void deleteFolder(@RequestParam("emailAddress") String emailAddress,@RequestParam("folderName") String folderName){
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        foldersService.deleteFolderByName(loginUser,folderName);
    }

    @PostMapping("/renameFolder")
    public void renameFolder(@RequestParam("emailAddress") String emailAddress,@RequestParam("folderName") String folderName, @RequestParam("folderNewName") String folderNewName) {
        User loginUser = emailAppRepository.getUserByEmail(emailAddress);
        foldersService.renameFolder(loginUser,folderName, folderNewName);
    }
}
