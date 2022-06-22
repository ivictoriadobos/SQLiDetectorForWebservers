package core.exceptions;

public class ClusterPhaseRuntimeException extends RuntimeException{
    private final CoreExceptionCauseEnum exceptionCause;

    public ClusterPhaseRuntimeException(CoreExceptionCauseEnum anExceptionCause) {
        super(anExceptionCause.getErrorMessage());
        exceptionCause = anExceptionCause;
    }

    public CoreExceptionCauseEnum getExceptionCauseCode()
    {
        return exceptionCause;
    }
}
