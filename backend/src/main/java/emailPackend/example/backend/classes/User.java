package emailPackend.example.backend.classes;

class User {
    private String userName;
    private String emailAddress;
    private String password;
    private List<Folder> folders;

    public User(Builder builder){
        this.userName = builder.userName;
        this.emailAddress = builder.emailAddress;
        this.password = builder.password;
        this.folders = builder.folders;
    }
    public createFolder(String folderName)
    {
        this.folders.add(new Folder(folderName));
    }
    public deleteFolder(String folderName)
    {
        
    }
    public sendEmail(String emailAddress , Email email)
    {

    }
    public readEmail(Email email)
    {

    }
    public createEmail()
    {
        
    }
    public static class Builder implements userBuilder{
        User newUser = new User();
        
    }
}
