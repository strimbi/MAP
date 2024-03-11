package Exceptions;

public class DuplicateElemException extends Exception{

    private final String message;

    public DuplicateElemException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
