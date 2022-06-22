package application.driver.interfaces;

import java.util.List;
import java.util.Map;

public interface ILog {

    public String getMethod();

    public Map<String, String> getHeaders();

    public Map<String, String> getQueryParameters();

    public Map<String, String> getBodyParameters();

    public String getSrcIPAddress();


}
