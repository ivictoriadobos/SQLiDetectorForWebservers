package core.clusterer.distancescore.function.branches;

import core.clusterer.distancescore.function.contracts.IManipulatorFunction;

public class SecondBranchFunction implements IManipulatorFunction {
    private final double lowerThreshold;

    private final double upperThreshold;

    public SecondBranchFunction(double aLowerThreshold, double anUpperThreshold) {
        lowerThreshold = aLowerThreshold;
        upperThreshold = anUpperThreshold;
    }

    @Override
    public double calculateScore(double logScore) { // [6 - 11]
        if (lowerThreshold<=logScore && logScore < upperThreshold)
        {
            return Math.pow(((logScore + 3.5)/3), 2);
        }

        return 0;
    }
}
