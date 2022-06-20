package application.driver.interfaces;

import java.util.Map;

public interface ILog {

    public Map<String, String> getHeaders();

    public String getMethod();

    public Map<String, String> getParameters();


}
