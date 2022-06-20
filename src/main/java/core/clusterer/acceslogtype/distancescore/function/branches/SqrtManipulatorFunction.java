package core.clusterer.acceslogtype.distancescore.function.branches;

import core.clusterer.acceslogtype.distancescore.function.contracts.IManipulatorFunction;

public class SqrtManipulatorFunction implements IManipulatorFunction {
    private double lowerThreshold;

    private double upperThreshold;

    public SqrtManipulatorFunction(double aLowerThreshold, double anUpperThreshold) {
        lowerThreshold = aLowerThreshold;
        upperThreshold = anUpperThreshold;
    }


    @Override
    public double calculateScore(double logScore) { // [2,)
        if (lowerThreshold < logScore && logScore < upperThreshold) {
            return Math.sqrt(2 * logScore) + 7.5;
        }

        return 0;
    }
}
