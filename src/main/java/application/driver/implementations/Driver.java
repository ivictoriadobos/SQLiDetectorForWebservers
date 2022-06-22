package application.driver.implementations;

import application.driver.exceptions.ApplicationException;
import application.driver.implementations.services.AnalysisServiceImpl;
import application.driver.interfaces.*;
import core.constants.AnaysisResultEnum;
import core.implementations.KNNLogClassifier;

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


            IAnalysisService analysisService = new AnalysisServiceImpl(new KNNLogClassifier(3));

            AnaysisResultEnum analysisResult = analysisService.analyseLog(aLog);


            if (!analysisResult.equals(AnaysisResultEnum.SAFE) &&
                    aLog.getMethod().equalsIgnoreCase("GET"))
            {
                    // instant reject and finish analysis
            }

            if ( analysisResult.equals(AnaysisResultEnum.INCONCLUSIVE))
            {
                // further analysis needed
                // classify if parameters that contain SQL commands expects such content

            }

            outputService.outputAnalysis(analysisService.getDetailedReport().get());

            }).start();
    }
}
