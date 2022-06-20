package core.clusterer.acceslogtype.distancescore;

import core.clusterer.acceslogtype.ApacheLog;
import core.clusterer.acceslogtype.distancescore.function.ScoreManipulatorFunction;
import core.clusterer.acceslogtype.distancescore.function.branches.*;

import java.util.ArrayList;

public class LogScoreCalculator {

    public static double[] calculate(ApacheLog apacheLog)
    {
        double[] score = new double[2];
        score[0] = 0;
        score[1] = 0;

        ScoreManipulatorFunction scoreManipulatorFunction = new ScoreManipulatorFunction(
                new ArrayList<>()
                {{
                    add(new FirstBranchFunction(0, 4.5));
                    add(new SecondBranchFunction(4.5, 11));
                    add(new ThirdBranchFunction(11, Integer.MAX_VALUE));
                }}
        );


        score[0] = scoreManipulatorFunction.calculateScore(apacheLog.getWeightedSumOfSQLKeywords() + apacheLog.getWeightedSumOfSpecialCharacters());
        System.out.println("Raw Score: " + apacheLog.getWeightedSumOfSQLKeywords() + apacheLog.getWeightedSumOfSpecialCharacters() + "\t Normalized value: " + score[0]);

        if (apacheLog.getHttpMethod().equalsIgnoreCase("POST"))
        {
            score[0] = 0;
            score[1] += 100;
        }
        else
        {
            score[0] += 100;
            score[1] = 0;
        }
        return score;
    }
}
