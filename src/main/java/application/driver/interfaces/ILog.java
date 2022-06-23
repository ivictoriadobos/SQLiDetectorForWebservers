package application.driver.interfaces;

import java.util.Map;
import java.util.Optional;

public interface ILog {

    public String getMethod();

    public Map<String, String> getHeaders();

    public Map<String, String> getQueryParameters();

    public Optional<Map<String, String>> getBodyParameters();

    public String getSrcIPAddress();


}
