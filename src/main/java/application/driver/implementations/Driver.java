package application.driver.implementations;

import application.driver.exceptions.ApplicationException;
import application.driver.exceptions.ApplicationExceptionCauseEnum;
import application.driver.implementations.services.HTTPRequestAnalysisServiceImpl;
import application.driver.interfaces.*;
import core.clusterer.acceslogtype.EuclideanDistanceMeasure;
import core.constants.AnalysisResultEnum;
import core.implementations.logclassifier.KNNLogClassifier;
import core.interfaces.ILogClassifier;
import org.apache.commons.math3.ml.distance.DistanceMeasure;

public class Driver implements IDriverClass {

    private final IInputService inputService;
    private final IOutputService outputService;

    public Driver(IInputService anInputService, IOutputService anOutputService) {
        inputService = anInputService;
        outputService = anOutputService;
    }

    @Override
    public void start() {

        System.out.println("Starting the analysis.. ");
        //noinspection InfiniteLoopStatement




        while (true)
        {
            ILog newLog;

            try {

                newLog = inputService.takeInput();
                launchNewAnalysis(newLog);
            }

            catch (ApplicationException ignored)
            {

            }
        }
    }

    @Override
    public void stop() {

    }

    private void launchNewAnalysis(ILog aLog)
    {
        new Thread(() -> {

            IAnalysisService analysisService = new HTTPRequestAnalysisServiceImpl(new KNNLogClassifier(5, new EuclideanDistanceMeasure()));

            IAnalysisReport analysisResult = analysisService.analyseLog(aLog);

            outputService.outputAnalysis(analysisResult);

            }).start();
    }
}
