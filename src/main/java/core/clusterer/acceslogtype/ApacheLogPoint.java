package core.clusterer.acceslogtype;

import application.driver.interfaces.ILog;
import core.clusterer.acceslogtype.distancescore.NormalizedLogScoreCalculator;
import core.interfaces.ILogPoint;
import core.interfaces.IParameter;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;
import java.util.Optional;

public class ApacheLogPoint implements ILogPoint {

    public String LogClass = "";

    private final ApacheLog apacheLog;
    private double[] logPoint;
    private final NormalizedLogScoreCalculator scoreCalculator;
    private final double weightedSumOfSQLKeywordsInPayload;
    private final double weightedSumOfSpecialCharacters;

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

    @Override
    public Optional<List<IParameter>> getInfectedParameters() {
        throw new NotImplementedException();
    }
}
