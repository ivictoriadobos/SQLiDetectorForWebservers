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

    private final IAnalysisService analysisService;

    private final DistanceMeasure distanceMeasure;

    private final ILogClassifier logClassifier;

    private final IOutputService outputService;

    public Driver(IInputService anInputService, IOutputService anOutputService) {
        inputService = anInputService;
        outputService = anOutputService;

        distanceMeasure = new EuclideanDistanceMeasure();
        logClassifier = new KNNLogClassifier(5, distanceMeasure);

        analysisService = new HTTPRequestAnalysisServiceImpl(logClassifier);
    }

    @Override
    public void start() {

        System.out.println("Starting the analysis.. ");

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

            IAnalysisReport analysisResult = analysisService.analyseLog(aLog);

            outputService.outputAnalysis(analysisResult);

            }).start();
    }
}
