package emailBackend.example.backend.validation;

import emailBackend.example.backend.repository.EmailAppRepository;

public class EmailCheckHandler implements UserHandler {
    private UserHandler next;
    private EmailAppRepository emailAppRepository;

    public EmailCheckHandler(EmailAppRepository emailAppRepository) {
        this.emailAppRepository = emailAppRepository;
    }

    @Override
    public void setNext(UserHandler nextHandler) {
        this.next = nextHandler;
    }

    @Override
    public void handle(UserContext context) {
        if (emailAppRepository.signupCheck(context.getEmail())) {
            throw new IllegalStateException("Email is taken");
        }
        if (next != null) {
            next.handle(context);
        }
    }
}
