package trax.exception;

public class EmptyDescriptionException extends TraxException {
    public EmptyDescriptionException(String taskType) {
        super("OOPS!!! The description of a " + taskType + " cannot be empty.");
    }
}
