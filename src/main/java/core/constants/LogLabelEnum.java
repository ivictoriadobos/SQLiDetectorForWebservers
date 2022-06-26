package core.constants;

import core.exceptions.CoreExceptionCauseEnum;
import core.exceptions.CoreException;

public enum LogLabelEnum {
    NORMAL_ACCESS(0),
    POSSIBLE_ATTACK(1),
    ATTACK(2);

    private final int value;
    LogLabelEnum(int aValue) {
        value = aValue;
    }

    public static LogLabelEnum labelOf(int idx) {
        switch (idx){
            case 0:
                return NORMAL_ACCESS;
            case 1:
                return POSSIBLE_ATTACK;
            case 2:
                return ATTACK;
        }
        throw new CoreException(CoreExceptionCauseEnum.INVALID_LOG_LABEL);
    }

    public int getValue() {
        return value;
    }
}
