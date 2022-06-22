package core.clusterer.acceslogtype.distancescore.function.branches;

import core.clusterer.acceslogtype.distancescore.function.contracts.IBranchFunction;

public class SecondBranchFunction implements IBranchFunction {
    private final double lowerThreshold;

    private final double upperThreshold;

    public SecondBranchFunction(double aLowerThreshold, double anUpperThreshold) {
        lowerThreshold = aLowerThreshold;
        upperThreshold = anUpperThreshold;
    }

    @Override
    public double computeNormalizedScore(double logScore) { // [6 - 11]
        if (lowerThreshold<=logScore && logScore < upperThreshold)
        {
            return Math.pow(((logScore + 3.5)/3), 2);
        }

        return 0;
    }
}
