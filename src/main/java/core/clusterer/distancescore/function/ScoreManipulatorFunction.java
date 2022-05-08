package core.clusterer.distancescore.function;

import core.clusterer.distancescore.function.contracts.IManipulatorFunction;

import java.util.List;

public class ScoreManipulatorFunction implements IManipulatorFunction {

    private final List<IManipulatorFunction> rangeManipulatorFunctions;

    public ScoreManipulatorFunction(List<IManipulatorFunction> aRangeManipulatorFunctions) {
        rangeManipulatorFunctions = aRangeManipulatorFunctions;
    }

    public double calculateScore(double logScore)
    {
        return rangeManipulatorFunctions.stream()
                .map(rangeManipulatorFunction -> rangeManipulatorFunction.calculateScore(logScore))
                .reduce(0.0, Double::sum);
    }
}
