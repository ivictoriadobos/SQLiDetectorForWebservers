package application.driver.interfaces;

import core.interfaces.IParameter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ILog {

    String getMethod();

    List<IParameter> getHeaders();

    List<IParameter> getQueryParameters();

    Optional<List<IParameter>> getBodyParameters();

    String getSrcIPAddress();

    String getTimeOfRequest();

}
