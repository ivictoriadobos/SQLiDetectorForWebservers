package application.driver.interfaces;

import core.constants.AnaysisResultEnum;

public interface IAnalysisService {

    AnaysisResultEnum analyseLog(ILog aLog);

    IAnalysisReport getDetailedReport();
}
