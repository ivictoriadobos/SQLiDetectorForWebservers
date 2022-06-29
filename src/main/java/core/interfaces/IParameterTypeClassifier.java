package core.interfaces;

import application.driver.interfaces.IAnalysisReport;
import core.constants.AnalysisResultEnum;

import java.util.List;

public interface IParameterTypeClassifier {

    IAnalysisReport classifyIfParametersExpectCommandsAsInput(ILogPoint aLogPointWithPossibleInfectedParameters);
}
