package core.clusterer.acceslogtype.distancescore.function;

import core.clusterer.acceslogtype.distancescore.function.contracts.IBranchFunction;

import java.util.List;

public class ScoreManipulatorFunction implements IBranchFunction {

    private final List<IBranchFunction> branchFunctions;

    public ScoreManipulatorFunction(List<IBranchFunction> aListOfBranchFunctions) {
        branchFunctions = aListOfBranchFunctions;
    }

    public double computeNormalizedScore(double anIntermediaryRawScore)
    {
        return branchFunctions.stream()
                .map(rangeManipulatorFunction -> rangeManipulatorFunction.computeNormalizedScore(anIntermediaryRawScore))
                .reduce(0.0, Double::sum);
    }
}
