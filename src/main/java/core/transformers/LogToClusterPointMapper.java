package core.transformers;

import core.clusterer.ApacheLogPoint;
import core.clusterer.ApacheLog;
import core.clusterer.LogScoreCalculator;

import java.util.Optional;

public class LogToClusterPointMapper {

    public static ApacheLogPoint fromApacheLog(ApacheLog apacheLog)
    {
        return new ApacheLogPoint(Optional.of(apacheLog.getOriginalUriQuery().length()).orElse(0),
                apacheLog.findOccurrencesNumberOfSQLKeywords(),
                0, // TBD
                apacheLog.findOccurrencesNumberOfWhiteSpaces(),
                apacheLog.findOccurrencesNumberOfSpecialCharacters(),
                apacheLog.LogClass,
                LogScoreCalculator.calculate(apacheLog),
                apacheLog);
    }
}
