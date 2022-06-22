package core.transformers;

import core.clusterer.acceslogtype.ApacheLogPoint;
import core.clusterer.acceslogtype.ApacheLog;
import core.clusterer.acceslogtype.distancescore.NormalizedLogScoreCalculator;

import java.util.Optional;

public class LogToClusterPointMapper {

    public static ApacheLogPoint fromApacheLog(ApacheLog apacheLog)
    {

        return new ApacheLogPoint(apacheLog.getWeightedSumOfSQLKeywords(),
                apacheLog.getWeightedSumOfSpecialCharacters(),
                apacheLog.LogClass,
                new NormalizedLogScoreCalculator(),
                apacheLog);
    }


}
