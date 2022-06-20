package core.clusterer.acceslogtype.distancescore.function.branches;

import core.clusterer.acceslogtype.distancescore.function.contracts.IManipulatorFunction;

public class FirstBranchFunction implements IManipulatorFunction {

    private final double lowerThreshold;

    private final double upperThreshold;

    public FirstBranchFunction(double aLowerThreshold, double anUpperThreshold) {
        lowerThreshold = aLowerThreshold;
        upperThreshold = anUpperThreshold;
    }

    @Override
    public double calculateScore(double logScore) { // 0 - 6
        if(lowerThreshold<=logScore && logScore<upperThreshold)
            return logScore;

        return 0;
    }
}
