package core.clusterer.acceslogtype.distancescore;

import core.clusterer.acceslogtype.distancescore.function.ScoreManipulatorFunction;
import core.clusterer.acceslogtype.distancescore.function.branches.*;

import java.util.ArrayList;

public class NormalizedLogScoreCalculator {

    public double compute(double anIntermediaryLogScore)
    {
        double score = 0;

        ScoreManipulatorFunction scoreManipulatorFunction = new ScoreManipulatorFunction(
                new ArrayList<>()
                {{
                    add(new FirstBranchFunction(0, 4.5));
                    add(new SecondBranchFunction(4.5, 11));
                    add(new ThirdBranchFunction(11, Integer.MAX_VALUE));
                }}
        );

        return scoreManipulatorFunction.computeNormalizedScore(anIntermediaryLogScore);
    }
}
