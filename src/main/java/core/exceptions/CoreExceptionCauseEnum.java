package core.exceptions;

public enum CoreExceptionCauseEnum {
    URL_DECODING_EXCEPTION("Error when decoding from url log format to standard utf."),
    TRAIN_LOG_FILE_NOT_FOUND("Path to file containing logs to be clustered leads to non-existing file."),
    PARSING_APACHE_LOG_EXCEPTION("There was an error when parsing the apache log."),
    INVALID_LOG_LABEL("The label with specified value does not exist."),
    LOG_CLASSIFIER_EXCEPTION("The log classifier encountered an unknown exception."),
    INVALID_PARAMETER_NAME("Parameter name is invalid."),
    INVALID_PARAMETER_VALUE("Parameter value is invalid."),
    INVALID_ANALYSIS_STATE("The analysis resulted in a invalid state."),
    METHOD_NOT_SUPPORTED("The request's method is not supported."),
    CANNOT_MODIFY_LOG_STRING_OF_ANALYSIS_REPORT("You're trying to modify a string log of an analysis report that's already set in place. Why?");

    private final String errorMessage;
    CoreExceptionCauseEnum(String anErrorMessage) {
        errorMessage = anErrorMessage;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }
}
