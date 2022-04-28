package core.exceptions;

public enum ExceptionCauseEnum {
    URL_DECODING_EXCEPTION("Error when decoding from url log format to standard utf"),
    LOG_FILE_NOT_FOUND("Path to file containing logs to be clustered leads to non-existing file"),
    PARSING_APACHE_LOG_EXCEPTION("There was an error when parsing the apache log");

    private final String errorMessage;
    ExceptionCauseEnum(String anErrorMessage) {
        errorMessage = anErrorMessage;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }
}
