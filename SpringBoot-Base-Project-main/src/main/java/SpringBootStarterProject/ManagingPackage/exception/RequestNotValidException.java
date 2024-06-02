package SpringBootStarterProject.ManagingPackage.exception;

public class RequestNotValidException extends RuntimeException {

    public RequestNotValidException(String message) {
        super(message);
    }

    public RequestNotValidException(String message, Throwable cause) {
        super (message ,cause);
    }
}
