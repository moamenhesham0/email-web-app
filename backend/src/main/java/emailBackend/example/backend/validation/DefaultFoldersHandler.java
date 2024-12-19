package emailBackend.example.backend.validation;

import java.util.ArrayList;
import java.util.List;

import emailBackend.example.backend.classes.Folder;
import emailBackend.example.backend.classes.User;

public class DefaultFoldersHandler implements UserHandler{
    private UserHandler next;

    @Override
    public void setNext(UserHandler nextHandler) {
        this.next = nextHandler;
    }

    @Override
    public void handle(UserContext context) {
        List<Folder> defaultFolders = new ArrayList<>();
        defaultFolders.add(new Folder.Builder().setFolderName("Inbox").build());
        defaultFolders.add(new Folder.Builder().setFolderName("Sent").build());
        defaultFolders.add(new Folder.Builder().setFolderName("Draft").build());
        defaultFolders.add(new Folder.Builder().setFolderName("Trash").build());

        User user = new User.Builder()
            .setUsername(context.getUserName())
            .setEmailAddress(context.getEmail())
            .setPassword(context.getPassword())
            .setFolders(defaultFolders)
            .setContacts(new ArrayList<>())
            .build();

        context.setUser(user);

        if (next != null) {
            next.handle(context);
        }
    }
}
