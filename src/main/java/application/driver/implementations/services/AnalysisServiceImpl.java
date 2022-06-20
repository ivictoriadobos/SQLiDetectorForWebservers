package application.driver.implementations.services;

import application.driver.interfaces.IAnalysisReport;
import application.driver.interfaces.IAnalysisService;
import application.driver.interfaces.ILog;
import core.constants.AnaysisResultEnum;

public class AnalysisServiceImpl implements IAnalysisService {
    @Override
    public AnaysisResultEnum analyseLog(ILog aLog) {

        // extract name_param + param_value

        //
        return null;
    }

    @Override
    public IAnalysisReport getDetailedReport() {
        return null;
    }
}
