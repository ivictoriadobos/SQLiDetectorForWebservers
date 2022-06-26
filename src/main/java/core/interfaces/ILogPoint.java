package core.interfaces;

import application.driver.interfaces.ILog;
import org.apache.commons.math3.ml.clustering.Clusterable;

import java.util.List;
import java.util.Optional;

public interface ILogPoint extends Clusterable {

    ILog getLog();

    Optional<List<IParameter>> getInfectedParameters();
}
