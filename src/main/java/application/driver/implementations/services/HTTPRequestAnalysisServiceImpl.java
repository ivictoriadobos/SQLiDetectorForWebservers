package application.driver.implementations.services;

import application.driver.implementations.models.SQLAnalysisReport;
import application.driver.interfaces.IAnalysisReport;
import application.driver.interfaces.IAnalysisService;
import application.driver.interfaces.ILog;
import core.clusterer.acceslogtype.distancescore.NormalizedLogScoreCalculator;
import core.constants.AnalysisResultEnum;
import core.constants.LogLabelEnum;
import core.exceptions.CoreException;
import core.exceptions.CoreExceptionCauseEnum;
import core.implementations.SQLCommandInQueryParamsOrHeadersFilterer;
import core.implementations.parametertypeclassifier.SQLParameterClassifier;
import core.interfaces.ICommandInQueryParamsOrHeadersFilter;
import core.interfaces.ILogClassifier;
import core.interfaces.ILogPoint;
import core.implementations.models.LogPoint;
import core.interfaces.IParameterTypeClassifier;

import java.util.Optional;

public class HTTPRequestAnalysisServiceImpl implements IAnalysisService {

    private final ILogClassifier logClassifier;

    private  ILogPoint logPoint;

    public HTTPRequestAnalysisServiceImpl(ILogClassifier aLogclassifier) {
        logClassifier = aLogclassifier;
    }

    @Override
    public IAnalysisReport analyseLog(ILog aLog) {

        IParameterTypeClassifier sqlParameterClassifier = new SQLParameterClassifier();
        ICommandInQueryParamsOrHeadersFilter sqlInQueryOrHeadersFilter = new SQLCommandInQueryParamsOrHeadersFilterer();


        logPoint = new LogPoint(aLog, new NormalizedLogScoreCalculator());

        LogLabelEnum logLabel =  logClassifier.classify(logPoint);

        var accessTypeAnalysisResult = analysisShouldContinue(logLabel, aLog.getMethod());

        if (accessTypeAnalysisResult.summary().get().equals(AnalysisResultEnum.INCONCLUSIVE))
        {
            if (sqlInQueryOrHeadersFilter.doesContainCommandInQueryParamsOrHeaders(logPoint))
            {
                accessTypeAnalysisResult.setAnalysisResult(AnalysisResultEnum.NOT_SAFE);
                return accessTypeAnalysisResult;
            }

            if (logPoint.getInfectedParameters().isEmpty())
            {
                throw new CoreException(CoreExceptionCauseEnum.INVALID_ANALYSIS_STATE);
            }

            return sqlParameterClassifier.classifyIfParametersExpectCommandsAsInput(logPoint.getInfectedParameters().get());
        }

        else return accessTypeAnalysisResult;
//        switch loglabel

        // if POST Request and loglabel!= SAFE
        // if request contains commands in headers/query params decline it
        // otherwise further analysis on body content if parameters containing sql commands should accept such thing



    }

    private SQLAnalysisReport analysisShouldContinue(LogLabelEnum aLogLabel, String aRequestMethod)
    {
        var result = new SQLAnalysisReport();

        if (aLogLabel.equals(LogLabelEnum.NORMAL_ACCESS))
        {
            result.setAnalysisResult(AnalysisResultEnum.SAFE);
        }

        else if ( aLogLabel.equals(LogLabelEnum.ATTACK))
        {
            result.setAnalysisResult(AnalysisResultEnum.NOT_SAFE);
        }

        else
        {
            if ( aRequestMethod.equalsIgnoreCase("GET"))
            {
                result.setAnalysisResult(AnalysisResultEnum.NOT_SAFE);
            }

            else
            {
                result.setAnalysisResult(AnalysisResultEnum.INCONCLUSIVE);
            }
        }

        return result;

        // TODO fix the logic here
    }
}
