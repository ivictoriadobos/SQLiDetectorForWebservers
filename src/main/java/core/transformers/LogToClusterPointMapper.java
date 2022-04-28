package core.transformers;

import core.clusterer.ApacheLogPoint;
import core.ApacheLog;

import java.util.Optional;

public class LogToClusterPointMapper {

    public static ApacheLogPoint fromApacheLog(ApacheLog apacheLog)
    {
        return new ApacheLogPoint(Optional.of(apacheLog.getOriginalUriQuery().length()).orElse(0),
                apacheLog.findOccurrencesNumberOfSQLKeywords(),
                0, // TBD
                apacheLog.findOccurrencesNumberOfWhiteSpaces(),
                apacheLog.findOccurrencesNumberOfSpecialCharacters());
    }
}
