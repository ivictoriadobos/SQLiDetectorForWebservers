package application.io.logprovider;

import application.driver.interfaces.ILog;
import core.clusterer.acceslogtype.ApacheLog;
import core.clusterer.acceslogtype.ApacheLogPoint;
import core.constants.LogLabelEnum;
import core.interfaces.ILogPoint;
import org.apache.commons.math3.ml.clustering.Cluster;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClassifiedLogPointsMapProvider {

    private static Map<LogLabelEnum, List<ILogPoint>> labeledLogPointsMap;

    public static Optional<Map<LogLabelEnum, List<ILogPoint>>> loadClassifiedLogPointsMapFromClustererResult(List<Cluster<ILogPoint>> clusters)
    {
        int idx = 0;

        for (Cluster<ILogPoint> cluster : clusters)
        {

            labeledLogPointsMap.put(LogLabelEnum.labelOf(idx), cluster.getPoints());

            idx++;
        }

        return Optional.of(labeledLogPointsMap);
    }

    public static Optional<Map<LogLabelEnum, List<ILogPoint>>>  getLogPointsMap()
    {
        return Optional.of(labeledLogPointsMap);
    }
}
