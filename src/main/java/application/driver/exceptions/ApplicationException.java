package application.driver.exceptions;

public class ApplicationException extends RuntimeException{

    private final ApplicationExceptionCauseEnum exceptionCause;
    public ApplicationException(ApplicationExceptionCauseEnum anExceptionCause)
    {
        super(anExceptionCause.getErrorMessage());
        exceptionCause = anExceptionCause;
    }

    public ApplicationExceptionCauseEnum getExceptionCauseCode()
    {
        return exceptionCause;
    }
}
