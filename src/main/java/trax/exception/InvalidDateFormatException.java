package trax.exception;

public class InvalidDateFormatException extends TraxException {
    public InvalidDateFormatException(String message) {
        super("OOPS!!! Invalid date format. " + message);
    }
}
