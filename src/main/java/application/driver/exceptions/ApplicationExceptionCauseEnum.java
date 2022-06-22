package application.driver.exceptions;

public enum ApplicationExceptionCauseEnum {
    INPUT_EXCEPTION("An exception occured at reading the input log."),
    INVALID_ARGUMENTS("Invalid number of arguments. Use --console for console I/O or --server for apache web server usage"),
    NULL_INPUT("Tried to read a new log, got EOF.");

    private final String errorMessage;
    ApplicationExceptionCauseEnum(String aMessage) {
        errorMessage = aMessage;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }
}
