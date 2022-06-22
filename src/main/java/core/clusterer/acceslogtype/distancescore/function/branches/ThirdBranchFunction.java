package core.clusterer.acceslogtype.distancescore.function.branches;

import core.clusterer.acceslogtype.distancescore.function.contracts.IBranchFunction;

public class ThirdBranchFunction implements IBranchFunction {
    private final double lowerThreshold;

    private final double upperThreshold;

    public ThirdBranchFunction(double aLowerThreshold, double anUpperThreshold) {
        lowerThreshold = aLowerThreshold;
        upperThreshold = anUpperThreshold;
    }


    @Override
    public double computeNormalizedScore(double logScore) { // [11,)
        if (lowerThreshold <= logScore && logScore < upperThreshold) {
            return Math.log(Math.pow(logScore, 2)) + 21.7;
        }

        return 0;
    }
}
