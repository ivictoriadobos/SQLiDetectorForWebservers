package application.driver.interfaces;

import core.constants.AnalysisResultEnum;

import java.util.Optional;

public interface IAnalysisReport {

    public Optional<AnalysisResultEnum> summary();

    public String fullDescription();
}
