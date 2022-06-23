package application.driver.implementations.services;

import application.driver.interfaces.IAnalysisReport;
import application.driver.interfaces.IAnalysisService;
import application.driver.interfaces.ILog;
import core.clusterer.acceslogtype.distancescore.NormalizedLogScoreCalculator;
import core.constants.AnaysisResultEnum;
import core.interfaces.ILogClassifier;
import core.interfaces.ILogPoint;
import core.implementations.models.LogPoint;

import java.util.Optional;

public class AnalysisServiceImpl implements IAnalysisService {

    private final ILogClassifier logClassifier;

    private  ILogPoint logPoint;

    public AnalysisServiceImpl(ILogClassifier aLogclassifier) {
        logClassifier = aLogclassifier;
    }

    @Override
    public AnaysisResultEnum analyseLog(ILog aLog) {

        logPoint = new LogPoint(aLog, new NormalizedLogScoreCalculator());

        var logLabel =  logClassifier.classify(logPoint);


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
