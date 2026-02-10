package trax.exception;

public class UnknownCommandException extends TraxException {
    public UnknownCommandException() {
        super("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }
}
