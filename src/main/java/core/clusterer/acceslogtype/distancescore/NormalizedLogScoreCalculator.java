package core.clusterer.acceslogtype.distancescore;

import core.clusterer.acceslogtype.distancescore.function.ScoreNormalizingFunction;
import core.clusterer.acceslogtype.distancescore.function.branches.*;
import core.constants.ClusterScoreThreshold;

import java.util.ArrayList;

public class NormalizedLogScoreCalculator {

    public double compute(double anIntermediaryLogScore)
    {
        double score = 0;

        ScoreNormalizingFunction scoreManipulatorFunction = new ScoreNormalizingFunction(
                new ArrayList<>()
                {{
                    add(new FirstBranchFunction(ClusterScoreThreshold.NORMAL_ACCESS_LOWER_THRESHOLD.getValue(), ClusterScoreThreshold.NORMAL_ACCESS_UPPER_THRESHOLD.getValue()));
                    add(new SecondBranchFunction(ClusterScoreThreshold.POSSIBLE_ATTACK_LOWER_THRESHOLD.getValue(), ClusterScoreThreshold.POSSIBLE_ATTACK_UPPER_THRESHOLD.getValue()));
                    add(new ThirdBranchFunction( ClusterScoreThreshold.ATTACK_LOWER_THRESHOLD.getValue(), ClusterScoreThreshold.ATTACK_UPPER_THRESHOLD.getValue()));
                }}
        );

        return scoreManipulatorFunction.computeNormalizedScore(anIntermediaryLogScore);
    }
}
