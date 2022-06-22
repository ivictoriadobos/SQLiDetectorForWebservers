package application.driver.interfaces;

import core.constants.AnaysisResultEnum;

import java.util.Optional;

public interface IAnalysisService {

    AnaysisResultEnum analyseLog(ILog aLog);

    Optional<IAnalysisReport> getDetailedReport();
}
