package core.interfaces;

import core.constants.LogLabelEnum;

public interface ILogClassifier {

    LogLabelEnum classify(ILogPoint aLogPoint);
}
