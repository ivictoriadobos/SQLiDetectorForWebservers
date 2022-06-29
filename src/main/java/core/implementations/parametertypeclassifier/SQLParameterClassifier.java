package core.implementations.parametertypeclassifier;

import application.driver.implementations.models.SQLAnalysisReport;
import application.driver.interfaces.IAnalysisReport;
import core.constants.AnalysisResultEnum;
import core.constants.ParameterClassTypeEnum;
import core.constants.ParameterTypePredictorMap;
import core.interfaces.ILogPoint;
import core.interfaces.IParameter;
import core.interfaces.IParameterTypeClassifier;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SQLParameterClassifier implements IParameterTypeClassifier {

    @Override
    public IAnalysisReport classifyIfParametersExpectCommandsAsInput(ILogPoint aLogPointWithPossibleInfectedParameters) {

        List<Pair<Double, IParameter>> parameterNameScore = new ArrayList<>();

        aLogPointWithPossibleInfectedParameters.getInfectedParameters().get().forEach(parameter ->
        {
            double score = 100;

            for (Map.Entry<String, ParameterClassTypeEnum> entry : ParameterTypePredictorMap.map.entrySet()) {

                Pattern namePattern = Pattern.compile(entry.getKey());
                Matcher matcher = namePattern.matcher(parameter.getName());

                if (matcher.results().count() != 0) {
                    switch (entry.getValue()) {
                        case SQL -> score += 10;
                        case NSQL -> score -= 10;
                    }
                }
            }

            parameterNameScore.add(new Pair<>(score, parameter));
        });

        // collection of parameters that are infected (contain SQL commands) but as their name suggest this shouldn't be the case
        var nonSQLParameters = parameterNameScore.stream().filter(pair -> pair.getFirst() < 100).collect(Collectors.toList());
        var SQLParameters = parameterNameScore.stream().filter(pair -> pair.getFirst() > 100).collect(Collectors.toList());
        var report = new SQLAnalysisReport();
        report.addLogString(aLogPointWithPossibleInfectedParameters.getLog().getLogAsString());


        if (nonSQLParameters.size() > 0 )
        {
            nonSQLParameters.forEach(scoreOfParameterPair ->
                    report.addInfectedParameter(scoreOfParameterPair.getValue()));

            report.addStatement("Spotted body parameters that are nonsqlparameters with sql commands as content - marking request as NOT SAFE");
            report.setAnalysisResult(AnalysisResultEnum.NOT_SAFE);
        }

        else if (SQLParameters.size() == aLogPointWithPossibleInfectedParameters.getInfectedParameters().get().size())
        {
            report.addStatement("All apparently infected body parameters are sql parameters - marking request as SAFE");
            report.setAnalysisResult(AnalysisResultEnum.SAFE);
        }

        else
        {
            report.addStatement("Couldn't decide if infected body parameters are either sqlparameters on nonsqlparameters - flag request");
            report.setAnalysisResult(AnalysisResultEnum.INCONCLUSIVE);
        }

        return report;
    }
}
