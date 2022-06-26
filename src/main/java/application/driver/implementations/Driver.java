package application.driver.implementations;

import application.driver.exceptions.ApplicationException;
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

        while (true)
        {
            try {

                ILog newLog = inputService.takeInput();

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


            if (!analysisResult.summary().get().equals(AnalysisResultEnum.SAFE))
            {
                    // instant reject and finish analysis
                System.out.println("Request contains sql commands in queryparams/headers. Rejecting it.");
                System.out.println(analysisResult.fullDescription());
                return;
            }

            if ( analysisResult.summary().get().equals(AnalysisResultEnum.INCONCLUSIVE))
            {
                // further analysis needed
                // classify if parameters that contain SQL commands expects such content

                System.out.println("Request contains SQL commands, not sure if safe. Human intervention needed.");
                System.out.println(analysisResult.fullDescription());
                return;

            }

            if ( analysisResult.equals(AnalysisResultEnum.NOT_SAFE))
            {
                System.out.println("Request contains too many sql commands in order to be safe. Rejecting it.");
                System.out.println(analysisResult.fullDescription());
                return;
            }

            outputService.outputAnalysis(analysisResult);

            }).start();
    }
}
