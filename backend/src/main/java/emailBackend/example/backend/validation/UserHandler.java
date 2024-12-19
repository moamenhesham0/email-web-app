package emailBackend.example.backend.validation;

public interface UserHandler {
    void setNext(UserHandler nextHandler);
    void handle(UserContext context);
}
