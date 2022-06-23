package core.implementations.models;

import application.driver.interfaces.ILog;
import core.clusterer.acceslogtype.distancescore.NormalizedLogScoreCalculator;
import core.constants.LogLabelEnum;
import core.constants.SQLKeywordAndWeight;
import core.constants.SQLSpecialCharacters;
import core.constants.WeightClassEnum;
import core.interfaces.ILogPoint;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogPoint implements ILogPoint {

    private final ILog log;

    private double[] logPoint;

    private NormalizedLogScoreCalculator scoreCalculator;
    private LogLabelEnum logLabel;

    public LogPoint(ILog aLog, NormalizedLogScoreCalculator aScoreCalculator) {
        log = aLog;
        scoreCalculator = aScoreCalculator;
    }

    private double computeWeightedSumOfSQLKeywordsInMap(Map<String, String> parameterMap) {
        double sqlKeywordsScore = 0;

        for (String parameterValue : parameterMap.values()) {
            sqlKeywordsScore += computeWeightedSumOfGivenMapForParameterValue(SQLKeywordAndWeight.keywordsAndWeight, parameterValue);
        }

        return sqlKeywordsScore;
    }

    private double computeWeightedSumOfSpecialCharactersInMap(Map<String, String> parameterMap)
    {
        double specialCharactersScore = 0;

        for (Map.Entry<String, String> mapEntry : parameterMap.entrySet()) {

            if (mapEntry.getKey().equalsIgnoreCase("User-Agent"))
            {
                specialCharactersScore += computeWeightedSumOfGivenMapForParameterValue(SQLSpecialCharacters.specialCharactersForUserAgent, mapEntry.getValue());
            }

            else
            {
                specialCharactersScore += computeWeightedSumOfGivenMapForParameterValue(SQLSpecialCharacters.specialCharacters, mapEntry.getValue());
            }
        }

        return specialCharactersScore;
    }

    private double computeWeightedSumOfGivenMapForParameterValue(Map<String, WeightClassEnum> anAttributeMap, String aParameterValue)
    {
        return anAttributeMap.keySet()
                .stream()
                .mapToDouble(key -> {

                    Pattern regexPattern = Pattern.compile(key);
                    Matcher matcher = regexPattern.matcher(aParameterValue);

                    return matcher.results().count() * anAttributeMap.get(key).getValue();
                })
                .sum();
    }

    @Override
    public double[] getPoint() {
        if (logPoint == null)
        {
            double intermediaryRawScore = computeWeightedSumOfSQLKeywordsInMap(log.getQueryParameters()) +
                                            computeWeightedSumOfSQLKeywordsInMap(log.getHeaders()) +

                                            computeWeightedSumOfSpecialCharactersInMap(log.getQueryParameters()) +
                                            computeWeightedSumOfSpecialCharactersInMap(log.getHeaders());

            if ( log.getBodyParameters().isPresent())
            {
                intermediaryRawScore += computeWeightedSumOfSQLKeywordsInMap(log.getBodyParameters().get()) + computeWeightedSumOfSpecialCharactersInMap(log.getBodyParameters().get());

            }

            logPoint = new double[] { intermediaryRawScore, scoreCalculator.compute(intermediaryRawScore)};
        }

        return logPoint;
    }

    public ILog getLog() {
        return log;
    }

}
