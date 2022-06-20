package application.driver.implementations;

import application.driver.exceptions.ApplicationException;
import application.driver.implementations.services.AnalysisServiceImpl;
import application.driver.interfaces.*;
import core.constants.AnaysisResultEnum;

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

            IAnalysisService analysisService = new AnalysisServiceImpl();

            AnaysisResultEnum analysisResult = analysisService.analyseLog(aLog);

            outputService.outputAnalysis(analysisService.getDetailedReport());

            }).start();
    }
}
