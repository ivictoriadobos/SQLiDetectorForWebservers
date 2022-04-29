package core.clusterer;

import core.ApacheLog;

public class LogScoreCalculator {

    public static double[] calculate(ApacheLog apacheLog)
    {
        double[] score = new double[2];

        if (apacheLog.getHttpMethod().equalsIgnoreCase("POST"))
        {
            score[0] = 0;
            score[1] = 100;
        }
        else
        {
            score[0] = 100;
            score[1] = 0;
        }

//        score[0] += apacheLog.findOccurrencesNumberOfSpecialCharacters()*2;
//        score[0] += apacheLog.findOccurrencesNumberOfSQLKeywords();
        score[0] += apacheLog.findOccurrencesNumberOfWhiteSpaces();
//        score[0] += apacheLog.getPayloadLength();

        return score;
    }
}
