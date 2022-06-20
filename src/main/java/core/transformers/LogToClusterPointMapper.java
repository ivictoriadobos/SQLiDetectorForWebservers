package core.transformers;

import core.clusterer.acceslogtype.ApacheLogPoint;
import core.clusterer.acceslogtype.ApacheLog;
import core.clusterer.acceslogtype.distancescore.LogScoreCalculator;

import java.util.Optional;

public class LogToClusterPointMapper {

    public static ApacheLogPoint fromApacheLog(ApacheLog apacheLog)
    {

        return new ApacheLogPoint(Optional.of(apacheLog.getOriginalUriQuery().length()).orElse(0),
                apacheLog.getNumberOfSQLKeywords(),
                apacheLog.getWeightedSumOfSQLKeywords(),
                apacheLog.findOccurrencesNumberOfWhiteSpaces(),
                apacheLog.getNumberOfSpecialCharacters(),
                apacheLog.getWeightedSumOfSpecialCharacters(),
                apacheLog.LogClass,
                LogScoreCalculator.calculate(apacheLog),
                apacheLog);
    }
}
