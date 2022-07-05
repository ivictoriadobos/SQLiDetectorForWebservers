package application.driver.implementations.services;

import application.driver.interfaces.IAnalysisReport;
import application.driver.interfaces.IOutputService;
import application.driver.interfaces.constants.ANSIColors;

public class ConsoleOutputServiceImpl implements IOutputService {
    @Override
    public void outputAnalysis(IAnalysisReport aReport) {

        String textColor = "";

        switch (aReport.analysisResult().get())
        {
            case SAFE -> textColor = ANSIColors.ANSI_GREEN;
            case INCONCLUSIVE -> textColor = ANSIColors.ANSI_YELLOW;
            case NOT_SAFE -> textColor = ANSIColors.ANSI_RED;
        }

//        System.out.println("alo politia");
        System.out.println("\n" + aReport.fullDescription() +textColor + "Analysis result: " + aReport.analysisResult().get().name() + ANSIColors.ANSI_RESET);

    }
}
