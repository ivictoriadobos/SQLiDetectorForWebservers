package core.interfaces;

import application.driver.interfaces.ILog;
import core.constants.LogLabelEnum;
import org.apache.commons.math3.ml.clustering.Clusterable;

public interface ILogPoint extends Clusterable {

    public ILog getLog();
}
