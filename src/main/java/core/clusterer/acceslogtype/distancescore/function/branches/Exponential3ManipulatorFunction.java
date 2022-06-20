package core.clusterer.acceslogtype.distancescore.function.branches;

import core.clusterer.acceslogtype.distancescore.function.contracts.IManipulatorFunction;

public class Exponential3ManipulatorFunction implements IManipulatorFunction {

    private double lowerThreshold;

    private double upperThreshold;

    public Exponential3ManipulatorFunction(double aLowerThreshold, double anUpperThreshold) {
        lowerThreshold = aLowerThreshold;
        upperThreshold = anUpperThreshold;
    }


    @Override
    public double calculateScore(double logScore) { // [0.75 - 2]
        if (lowerThreshold<logScore && logScore < upperThreshold)
        {
            return Math.pow(3, (logScore+0.18))-2;
        }

        return 0;
    }
}
