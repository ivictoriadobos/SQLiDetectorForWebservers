package core.implementations;

import core.constants.AnaysisResultEnum;
import core.constants.LogLabelEnum;
import core.interfaces.ILogClassifier;
import core.interfaces.ILogPoint;

import java.util.List;
import java.util.Map;

public class KNNLogClassifier implements ILogClassifier {
    private int n;

    public KNNLogClassifier(int aNeighboursNumber) {
        n = aNeighboursNumber;
    }

    @Override
    public AnaysisResultEnum classify(ILogPoint aLogPoint) {

        // get closest n labeledLogPoints to aLogPoint. you take them from ClassifiedLogPointsMapProvider

        // compute the preponderent label in a pondered manner

        // return computed label. add current logpoint to map of points

        return AnaysisResultEnum.SAFE;
    }
}
