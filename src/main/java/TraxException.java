class TraxException extends Exception {
    public TraxException(String message) {
        super(message);
    }
}

class EmptyDescriptionException extends TraxException {
    public EmptyDescriptionException(String taskType) {
        super("OOPS!!! The description of a " + taskType + " cannot be empty.");
    }
}

class UnknownCommandException extends TraxException {
    public UnknownCommandException() {
        super("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }
}

class InvalidFormatException extends TraxException {
    public InvalidFormatException(String message) {
        super("OOPS!!! " + message);
    }
}

class InvalidTaskNumberException extends TraxException {
    public InvalidTaskNumberException() {
        super("OOPS!!! Invalid task number.");
    }
}

class InvalidDateFormatException extends TraxException {
    public InvalidDateFormatException(String message) {
        super("OOPS!!! Invalid date format. " + message);
    }
}
