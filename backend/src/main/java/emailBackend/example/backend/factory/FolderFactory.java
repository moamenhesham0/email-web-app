package emailBackend.example.backend.factory;

import emailBackend.example.backend.classes.Folder;

public class FolderFactory {

    public static Folder creatFolder(String folderName){
        Folder newFolder = new Folder();
        newFolder.setFolderName(folderName);
        return newFolder;
    }
}
