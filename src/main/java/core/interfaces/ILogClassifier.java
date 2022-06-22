package core.interfaces;

import core.constants.AnaysisResultEnum;

public interface ILogClassifier {

    AnaysisResultEnum classify(ILogPoint aLogPoint);
}
