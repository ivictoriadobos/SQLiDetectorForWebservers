package core.exceptions;

public class ClusterPhaseRuntimeException extends RuntimeException{
    private final ExceptionCauseEnum exceptionCause;

    public ClusterPhaseRuntimeException(ExceptionCauseEnum anExceptionCause) {
        super(anExceptionCause.getErrorMessage());
        exceptionCause = anExceptionCause;
    }

    public ExceptionCauseEnum getExceptionCauseCode()
    {
        return exceptionCause;
    }
}
