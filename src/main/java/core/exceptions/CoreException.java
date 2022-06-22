package core.exceptions;

public class CoreException extends RuntimeException {
    private final CoreExceptionCauseEnum exceptionCause;

    public CoreException(CoreExceptionCauseEnum anExceptionCause) {
        super(anExceptionCause.getErrorMessage());
        exceptionCause = anExceptionCause;
    }

    public CoreExceptionCauseEnum getExceptionCauseCode()
    {
        return exceptionCause;
    }
}
