package core.clusterer.distancescore.function.branches;

import core.clusterer.distancescore.function.contracts.IManipulatorFunction;

public class LinearManipulatorFunction implements IManipulatorFunction {

    private double lowerThreshold;

    private double upperThreshold;

    public LinearManipulatorFunction(double aLowerThreshold, double anUpperThreshold) {
        lowerThreshold = aLowerThreshold;
        upperThreshold = anUpperThreshold;
    }

    @Override
    public double calculateScore(double logScore) {
        if(lowerThreshold<logScore && logScore<upperThreshold)
            return logScore;

        return 0;
    }
}
