package application.driver.exceptions;

public enum ApplicationExceptionCauseEnum {
    INPUT_EXCEPTION("An exception occured at reading the input log."),
    INVALID_ARGUMENTS("Invalid number of arguments. Use --console for console I/O or --server for apache web server usage"),
    NULL_INPUT("Tried to read a new log, got EOF."),
    EXCEPTION_AT_STARTING_BASH_COMMAND("Exception at launching tshark command as a process."),
    EXCEPTION_AT_READING_CONTINUOUS_INPUT_FROM_TSHARK("An unexpected exception occured when reading tshark output."),
    EXCEPTION_AT_PARSING_LOG("Exception encountered at parsing a line to a Log object");

    private final String errorMessage;
    ApplicationExceptionCauseEnum(String aMessage) {
        errorMessage = aMessage;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }
}
