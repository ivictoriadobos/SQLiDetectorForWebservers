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

    private LogLabelEnum logLabel = null;
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
        computeLogPointScore();

        apacheLog = anApacheLog;
    }

    @Override
    public double[] getPoint() {

        return logPoint;
    }

    public double getIntermediaryScore()
    {
        return weightedSumOfSQLKeywordsInPayload + weightedSumOfSpecialCharacters;
    }

    @Override
    public LogLabelEnum getLabel() {

        return logLabel;
    }

    public void setLabel(LogLabelEnum aLogLabel)
    {
        logLabel = aLogLabel;
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
