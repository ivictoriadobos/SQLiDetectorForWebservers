package application.driver.implementations.services;

import application.driver.interfaces.IAnalysisReport;
import application.driver.interfaces.IOutputService;
import application.driver.interfaces.constants.ANSIColors;

public class ConsoleOutputServiceImpl implements IOutputService {
    @Override
    public void outputAnalysis(IAnalysisReport aReport) {

        String backgroundColor = "";

        switch (aReport.analysisResult().get())
        {
            case SAFE -> backgroundColor = ANSIColors.ANSI_GREEN;
            case INCONCLUSIVE -> backgroundColor = ANSIColors.ANSI_YELLOW;
            case NOT_SAFE -> backgroundColor = ANSIColors.ANSI_RED;
        }

        System.out.println("\n" + aReport.fullDescription() +backgroundColor + "Analysis result: " + aReport.analysisResult().get().name() + ANSIColors.ANSI_RESET);

    }
}
