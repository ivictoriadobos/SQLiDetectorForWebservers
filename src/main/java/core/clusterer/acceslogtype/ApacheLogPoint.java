package core.clusterer.acceslogtype;

import application.driver.interfaces.ILog;
import core.clusterer.acceslogtype.distancescore.NormalizedLogScoreCalculator;
import core.constants.LogLabelEnum;
import core.interfaces.ILogPoint;
import org.apache.commons.math3.ml.clustering.Clusterable;

public class ApacheLogPoint implements ILogPoint {

    public String LogClass = "";

    private ApacheLog apacheLog;
    private double[] logPoint;
    private NormalizedLogScoreCalculator scoreCalculator;
    private double weightedSumOfSQLKeywordsInPayload;
    private double weightedSumOfSpecialCharacters;

    public ApacheLogPoint(double aWeightedSumOfSQLKeywords,
                          double aWeightedSumOfSpecialCharacters,
                          String aLogClass,
                          NormalizedLogScoreCalculator aScoreCalculator,
                          ApacheLog anApacheLog) {

        weightedSumOfSQLKeywordsInPayload = aWeightedSumOfSQLKeywords;
        weightedSumOfSpecialCharacters = aWeightedSumOfSpecialCharacters;

        LogClass = aLogClass;

        scoreCalculator = aScoreCalculator;

        apacheLog = anApacheLog;
    }

    @Override
    public double[] getPoint() {

        if (logPoint == null) {
            computeLogPointScore();
        }

        return logPoint;
    }

    public double getIntermediaryScore()
    {
        return weightedSumOfSQLKeywordsInPayload + weightedSumOfSpecialCharacters;
    }

    @Override
    public ILog getLog() {
        return apacheLog;
    }

    private void computeLogPointScore()
    {
        logPoint = new double[]{0,0};

        logPoint[0] = weightedSumOfSpecialCharacters  + weightedSumOfSQLKeywordsInPayload;

        logPoint[1] = scoreCalculator.compute(logPoint[0]);
    }
}
