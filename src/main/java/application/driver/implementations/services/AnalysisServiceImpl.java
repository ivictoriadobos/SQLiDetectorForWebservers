package application.driver.implementations.services;

import application.driver.interfaces.IAnalysisReport;
import application.driver.interfaces.IAnalysisService;
import application.driver.interfaces.ILog;
import core.constants.AnaysisResultEnum;
import core.interfaces.ILogClassifier;
import core.interfaces.ILogPoint;
import core.models.LogPoint;

import java.util.Optional;

public class AnalysisServiceImpl implements IAnalysisService {

    private final ILogClassifier classifier;

    private  ILogPoint logPoint;

    public AnalysisServiceImpl(ILogClassifier aLogclassifier) {
        classifier = aLogclassifier;
    }

    @Override
    public AnaysisResultEnum analyseLog(ILog aLog) {

        logPoint = new LogPoint(aLog);

        return classifier.classify(logPoint);
    }

    @Override
    public Optional<IAnalysisReport> getDetailedReport() {

        if ( logPoint!= null )
        {
            return null; // TODO
        }

        else return Optional.empty();
    }
}
