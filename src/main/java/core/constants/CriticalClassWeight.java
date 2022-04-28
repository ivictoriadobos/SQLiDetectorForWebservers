package core.constants;

import java.util.HashMap;
import java.util.Map;

public class CriticalClassWeight {
    public static final Map<String, Double> keywordClassToWeight = new HashMap<>() {{
        put("HIGH", 50.0);
        put("MEDIUM", 30.0);
        put("LOW", 10.0);
    }};

}
