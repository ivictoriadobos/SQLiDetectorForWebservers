package application.driver.implementations.models;

import application.driver.interfaces.IAnalysisReport;
import core.constants.AnalysisResultEnum;
import core.exceptions.CoreException;
import core.exceptions.CoreExceptionCauseEnum;
import core.interfaces.IParameter;
import java.util.List;
import java.util.Optional;

public class SQLAnalysisReport implements IAnalysisReport {

        private List<IParameter> infectedParameters;

        private AnalysisResultEnum analysisResult;

    public SQLAnalysisReport() {
    }

    @Override
    public Optional<AnalysisResultEnum> summary() {

        return Optional.ofNullable(analysisResult);
    }

    @Override
    public String fullDescription() {

        if (analysisResult!= null && analysisResult.equals(AnalysisResultEnum.SAFE))
        {
            return "The analysed request turned out to be a safe one.";
        }

        else if ( infectedParameters != null && infectedParameters.size() > 0)
        {
            return "The analysed request turned out to be a malicious one.";
        }

        else throw new CoreException(CoreExceptionCauseEnum.INVALID_ANALYSIS_STATE);
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
