package core.constants;

import java.util.HashMap;
import java.util.Map;

public class SQLKeywordsAndWeight {

    public static Map<String, Double> keywordsAndWeight = new HashMap<String, Double>() {{
        put("\\s+UNION\\s+", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("TRUNCATE[\\s\\(]+", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("EXEC[\\s\\(]+", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("SELECT[\\s\\(]+", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("DUAL", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("SYSDATE", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("DBMS", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("RLIKE", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("MAKE_SET", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("ELT", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("NVL[\\s\\(]+", CriticalClassWeight.keywordClassToWeight.get("HIGH"));

        put("TABLE", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("DELETE", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("UPDATE", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("INSERT\\s+", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("COUNT[\\s\\(]+", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("WHERE", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("GROUP BY", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("ORDER BY\\s+", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("DROP\\s+(table|database)", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("SLEEP\\s+", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("CASE", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("LIKE\\s+", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));

        put("\\s+AND\\s+", CriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("\\s+OR\\s+", CriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("\\s+INTO\\s+", CriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("\\s+FROM\\s+", CriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("BEGIN\\s+", CriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("END", CriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("ELSE", CriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("THEN", CriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("\\s+AS\\s+", CriticalClassWeight.keywordClassToWeight.get("LOW"));
    }};
}
