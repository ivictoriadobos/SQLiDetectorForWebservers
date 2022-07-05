package core.implementations.models;

import application.driver.interfaces.ILog;
import core.clusterer.acceslogtype.distancescore.NormalizedLogScoreCalculator;
import core.constants.*;
import core.interfaces.ILogPoint;
import core.interfaces.IParameter;
import java.util.*;

public class LogPoint implements ILogPoint {

    private final ILog log;

    private double[] logPoint;

    private List<IParameter> possibleInfectedBodyParametersMap;
    private final NormalizedLogScoreCalculator attackScoreCalculator;
    private LogLabelEnum logLabel;

    public LogPoint(ILog aLog, NormalizedLogScoreCalculator aScoreCalculator) {
        log = aLog;
        attackScoreCalculator = aScoreCalculator;

        computePoint();
    }

    private double computeIntermediaryRawScoreOfList(List<IParameter> aParameterList) {

        double sqlKeywordsScore = 0;

        for (IParameter parameter : aParameterList) {

            sqlKeywordsScore += parameter.getIntermediaryPartialRawScore();
        }

        return sqlKeywordsScore;
    }

    @Override
    public double[] getPoint() {
        return logPoint;
    }

    public ILog getLog() {
        return log;
    }

    @Override
    public Optional<List<IParameter>> getInfectedParameters() {
        return Optional.ofNullable(possibleInfectedBodyParametersMap);
    }

    private void computePoint()
    {
        double intermediaryRawScore = computeIntermediaryRawScoreOfList(log.getHeaders())
                                    + computeIntermediaryRawScoreOfList(log.getQueryParameters());

        if ( log.getBodyParameters().isPresent())
        {
            intermediaryRawScore += computeIntermediaryRawScoreOfList(log.getBodyParameters().get());

            trackPossibleInfectedBodyParameters();
        }

        logPoint = new double[] { intermediaryRawScore, attackScoreCalculator.compute(intermediaryRawScore)};
    }

    private void trackPossibleInfectedBodyParameters()
    {
        possibleInfectedBodyParametersMap= new ArrayList<>();

        log.getBodyParameters().get().forEach( parameter ->
        {

            if ( parameter.getIntermediaryPartialRawScore() >= ClusterScoreThreshold.POSSIBLE_ATTACK_LOWER_THRESHOLD.getValue())
            {
                possibleInfectedBodyParametersMap.add(parameter);
            }
        });
    }
}
