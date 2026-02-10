package trax.exception;

public class InvalidFormatException extends TraxException {
    public InvalidFormatException(String message) {
        super("OOPS!!! " + message);
    }
}
