package application.driver.interfaces;

import core.constants.AnalysisResultEnum;

import java.util.Optional;

public interface IAnalysisReport {

    public Optional<AnalysisResultEnum> analysisResult();

    public String fullDescription();

    public void addStatement(String aStatement);

    public void addLogString(String aLogString);
}
