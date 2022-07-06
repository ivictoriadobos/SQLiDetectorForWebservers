package core.implementations.logclassifier;

import application.io.logprovider.ClassifiedLogPointsMapProvider;
import core.constants.LogLabelEnum;
import core.exceptions.CoreException;
import core.exceptions.CoreExceptionCauseEnum;
import core.interfaces.ILogClassifier;
import core.interfaces.ILogPoint;
import org.apache.commons.math3.ml.distance.DistanceMeasure;
import org.apache.commons.math3.util.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class KNNLogClassifier implements ILogClassifier {
    private final int n;

    private final DistanceMeasure distanceMeasure;

    public KNNLogClassifier(int aNeighboursNumber, DistanceMeasure aDistanceMeasure) {
        n = aNeighboursNumber;
        distanceMeasure = aDistanceMeasure;
    }

    @Override
    public LogLabelEnum classify(ILogPoint aLogPoint) {

        // get closest n labeledLogPoints to aLogPoint. you take them from ClassifiedLogPointsMapProvider

        var closestNeighbours = getClosestKNeighbours(aLogPoint);
        List<Pair<LogLabelEnum, Double>> classesRank = new ArrayList<>();

        closestNeighbours.forEach((logLabel, pairs) ->
                {
                    var ponderedDistanceForLabel = pairs.stream().reduce(0.0, (subtotal, element) -> subtotal + 1/(1+element.getValue()), Double::sum);
                    classesRank.add(new Pair<>(logLabel, ponderedDistanceForLabel));
                });

        classesRank.sort((o1, o2) -> (int)(o2.getValue() - o1.getValue()));
        classesRank.sort((o1, o2) -> (int)(o2.getValue() - o1.getValue()));
        classesRank.sort((o1, o2) -> (int)(o2.getValue() - o1.getValue()));

        var closestClass = new Pair<LogLabelEnum, Double>(LogLabelEnum.ATTACK, (double) 0);

        for (var cls : classesRank)
        {
            if (cls.getValue() > closestClass.getValue())
                closestClass = cls;
        }


        try {
            return closestClass.getKey();
        }

        catch (Exception e) {
            throw new CoreException(CoreExceptionCauseEnum.LOG_CLASSIFIER_EXCEPTION);
        }
    }

    private Map<LogLabelEnum, List<Pair<ILogPoint, Double>>> getClosestKNeighbours(ILogPoint aLogPoint)
    {

        Map<LogLabelEnum, List<Pair<ILogPoint, Double>>> logLabelToListOfLogPointsAndDistanceMap = new HashMap<>();

        Map<LogLabelEnum, List<Pair<ILogPoint, Double>>> toBeReturned = new HashMap<>();
        toBeReturned.put(LogLabelEnum.NORMAL_ACCESS, new ArrayList<>());
        toBeReturned.put(LogLabelEnum.POSSIBLE_ATTACK, new ArrayList<>());
        toBeReturned.put(LogLabelEnum.ATTACK, new ArrayList<>());


        ClassifiedLogPointsMapProvider.getLogPointsMap().ifPresent(logPointsMap -> {
            for ( Map.Entry<LogLabelEnum, List<ILogPoint>> mapEntry : logPointsMap.entrySet())
            {
                List<Pair<ILogPoint, Double>> logPointsAndDistancesPairsList = new ArrayList<>();
                mapEntry.getValue().forEach(logPoint ->
                {
                    logPointsAndDistancesPairsList.add(new Pair<>(logPoint, distanceMeasure.compute(aLogPoint.getPoint(), logPoint.getPoint())));
                });

                logLabelToListOfLogPointsAndDistanceMap.put(mapEntry.getKey(), logPointsAndDistancesPairsList);
            }

            // sort each list of pairs by distance ASC (least distance first)
            logLabelToListOfLogPointsAndDistanceMap.forEach((logLabelEnum, pairs) -> pairs.sort((o1, o2) -> {
                if ( o1.getValue() > o2.getValue())
                    return 1;
                if ( o1.getValue() < o2.getValue())
                    return -1;
                return 0;
            }));

            List<Pair<LogLabelEnum, Pair<ILogPoint, Double>>> nNeighboursFromEachClass = new ArrayList<>();

            for ( int i = 0; i< n ; i++ )
            {
                int idx = i;
                logLabelToListOfLogPointsAndDistanceMap.forEach(((logLabelEnum, pairs) -> {
                    nNeighboursFromEachClass.add(new Pair<>(logLabelEnum, pairs.get(idx)));
                }));
            }

            // sort closest neighbours in ASC orderd
            nNeighboursFromEachClass.sort((o1,o2)-> (int) (o1.getValue().getValue() - o2.getValue().getValue()));

            // take only n neighbours and return them
            nNeighboursFromEachClass.stream()
                                    .limit(n)
                                    .forEach(logLabelEnumPairPair -> {
                                        toBeReturned.get(logLabelEnumPairPair.getKey()).add(logLabelEnumPairPair.getValue());
                                    });

        });

        return toBeReturned;
    }

}
