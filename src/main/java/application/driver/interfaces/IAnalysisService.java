package application.driver.interfaces;

import core.constants.AnalysisResultEnum;

import java.util.Optional;

public interface IAnalysisService {

    IAnalysisReport analyseLog(ILog aLog);
}
