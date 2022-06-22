package core.models;

import application.driver.interfaces.ILog;
import core.constants.LogLabelEnum;
import core.interfaces.ILogPoint;

public class LogPoint implements ILogPoint {

    private final ILog log;

    private double[] logPoint;

    private LogLabelEnum logLabel;

    public LogPoint(ILog aLog) {
        log = aLog;
    }

    public ILog getLog() {
        return log;
    }

    private double computeWeightedSumOfSQLKeywordsInHeaders()
    {
        return Double.parseDouble(null);
    }

    private double computeWeightedSumOfSQLKeywordsInQueryParameters()
    {
        return Double.parseDouble(null);
    }

    private double computeWeightedSumOfSQLKeywordsInBodyParameters()
    {
        return Double.parseDouble(null);
    }

    @Override
    public double[] getPoint() {
        return new double[0];
    }

    @Override
    public LogLabelEnum getLabel() {
        return logLabel;
    }
}
