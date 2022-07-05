package application.driver.implementations.models;

import application.driver.interfaces.IAnalysisReport;
import application.driver.interfaces.ILog;
import core.constants.AnalysisResultEnum;
import core.exceptions.CoreException;
import core.exceptions.CoreExceptionCauseEnum;
import core.interfaces.IParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLAnalysisReport implements IAnalysisReport {

        private List<IParameter> infectedParameters;

        private AnalysisResultEnum analysisResult;

        private List<String> statements;

        private ILog log;

    public SQLAnalysisReport() {
        infectedParameters = new ArrayList<>();
        statements = new ArrayList<>();
    }

    @Override
    public Optional<AnalysisResultEnum> analysisResult() {

        return Optional.ofNullable(analysisResult);
    }

    @Override
    public String fullDescription() {

        // iterate over statements list
        // if infected parameters are not null, print them aswell
//         throw new CoreException(CoreExceptionCauseEnum.INVALID_ANALYSIS_STATE);

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Request: ").append(log.getLogAsString()).append("\n");

        statements.forEach(statement ->
        {
            stringBuilder.append(statement).append("\n");
        });

        log.getBodyParameters().ifPresent(listOfParameters ->
        {
            stringBuilder.append("Body parameters: \n");
            listOfParameters.forEach(bodyParameter ->
            {
                stringBuilder.append(bodyParameter.getName()).append(": ").append(bodyParameter.getValue()).append("\n");
            });
        });

        if (!infectedParameters.isEmpty()) {
            stringBuilder.append("Infected parameters: \n");

            infectedParameters.forEach(parameter ->
            {
                stringBuilder.append(parameter.getName()).append(": ").append(parameter.getValue()).append("\n");
            });
        }

//        stringBuilder.append("Analysis result: " + analysisResult.name());
        return stringBuilder.toString();
    }

    @Override
    public void addStatement(String aStatement) {
         statements.add(aStatement);
    }

    @Override
    public void addLog(ILog aLog) {
        if(log != null)
        {
            throw new CoreException(CoreExceptionCauseEnum.CANNOT_MODIFY_LOG_OF_ANALYSIS_REPORT);
        }

        log = aLog;
    }

    public void setAnalysisResult(AnalysisResultEnum anAnalysisResult) {
        analysisResult = anAnalysisResult;
    }

    public void addInfectedParameter(IParameter aParameter)
    {
        if (aParameter != null)
        {
            infectedParameters.add(aParameter);
        }

        else throw new CoreException(CoreExceptionCauseEnum.INVALID_ANALYSIS_STATE);
    }
}
