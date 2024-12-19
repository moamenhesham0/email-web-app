package emailBackend.example.backend.validation;

import emailBackend.example.backend.repository.EmailAppRepository;

public class SaveUserHandler implements UserHandler{
        private EmailAppRepository emailAppRepository;

    public SaveUserHandler(EmailAppRepository emailAppRepository) {
        this.emailAppRepository = emailAppRepository;
    }

    @Override
    public void setNext(UserHandler nextHandler) {
        // This is the last handler in the chain
    }

    @Override
    public void handle(UserContext context) {
        emailAppRepository.saveUserInSystem(context.getUser());
    }
}
