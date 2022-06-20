package application.driver.exceptions;

public enum ExceptionReasonEnum {
    INPUT_EXCEPTION("An exception occured at reading the input log."),
    INVALID_ARGUMENTS("Invalid number of arguments. Use --console for console I/O or --server for apache web server usage"),
    NULL_INPUT("Tried to read a new log, got EOF.");

    private final String message;
    ExceptionReasonEnum(String aMessage) {
        message = aMessage;
    }

    public String getMessage() {
        return message;
    }
}
