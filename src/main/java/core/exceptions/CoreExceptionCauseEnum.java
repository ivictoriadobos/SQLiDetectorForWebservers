package core.exceptions;

public enum CoreExceptionCauseEnum {
    URL_DECODING_EXCEPTION("Error when decoding from url log format to standard utf."),
    LOG_FILE_NOT_FOUND("Path to file containing logs to be clustered leads to non-existing file."),
    PARSING_APACHE_LOG_EXCEPTION("There was an error when parsing the apache log."),

    INVALID_LOG_LABEL("The label with specified value does not exist.");

    private final String errorMessage;
    CoreExceptionCauseEnum(String anErrorMessage) {
        errorMessage = anErrorMessage;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }
}