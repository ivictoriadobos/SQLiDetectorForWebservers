package application.driver.implementations.models;

import application.driver.interfaces.IAnalysisReport;
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

        private String logString;

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

        stringBuilder.append("Request: " +  logString + "\n");

        statements.forEach(statement ->
        {
            stringBuilder.append(statement + "\n");
        });

//        stringBuilder.append("Analysis result: " + analysisResult.name());
        return stringBuilder.toString();
    }

    @Override
    public void addStatement(String aStatement) {
         statements.add(aStatement);
    }

    @Override
    public void addLogString(String aLogString) {
        if(logString != null && !logString.equals(aLogString))
        {
            throw new CoreException(CoreExceptionCauseEnum.CANNOT_MODIFY_LOG_STRING_OF_ANALYSIS_REPORT);
        }

        logString = aLogString;
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
