package application.driver.implementations;

import application.driver.exceptions.ApplicationException;
import application.driver.exceptions.ApplicationExceptionCauseEnum;
import application.driver.implementations.services.HTTPRequestAnalysisServiceImpl;
import application.driver.interfaces.*;
import core.clusterer.acceslogtype.EuclideanDistanceMeasure;
import core.constants.AnalysisResultEnum;
import core.implementations.logclassifier.KNNLogClassifier;

public class Driver implements IDriverClass {

    private final IInputService inputService;

    private final IOutputService outputService;

    public Driver(IInputService inputService, IOutputService outputService) {
        this.inputService = inputService;
        this.outputService = outputService;
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


            IAnalysisService analysisService = new HTTPRequestAnalysisServiceImpl(new KNNLogClassifier(5, new EuclideanDistanceMeasure()));

            IAnalysisReport analysisResult = analysisService.analyseLog(aLog);

            outputService.outputAnalysis(analysisResult);

            return;
//
//            if (analysisResult.analysisResult().get().equals(AnalysisResultEnum.SAFE))
//            {
//                return;
//            }
//
//            if ( analysisResult.analysisResult().get().equals(AnalysisResultEnum.INCONCLUSIVE))
//            {
//
//                System.out.println("Request contains SQL commands in body parameters, not sure if safe. Human intervention needed.");
//                return;
//
//            }
//
//            if ( analysisResult.analysisResult().get().equals(AnalysisResultEnum.NOT_SAFE))
//            {
//                System.out.println("Request contains too many sql commands in body parameters in order to be safe. Rejecting it.");
//                return;
//            }
//
//
//

            }).start();
    }
}
