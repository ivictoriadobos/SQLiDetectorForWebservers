package core.constants;

import java.util.HashMap;
import java.util.Map;

public class SQLKeywordsAndWeight {

    public static Map<String, Double> keywordsAndWeight = new HashMap<String, Double>() {{
        put("UNION", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("TRUNCATE", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("EXEC", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("SELECT", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("DUAL", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("SYSDATE", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("DBMS", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("RLIKE", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("MAKE_SET", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("ELT", CriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("NVL", CriticalClassWeight.keywordClassToWeight.get("HIGH"));

        put("TABLE", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("DELETE", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("UPDATE", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("INSERT", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("COUNT", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("WHERE", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("GROUP", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("ORDER", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("DROP", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("SLEEP", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("CASE", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("LIKE", CriticalClassWeight.keywordClassToWeight.get("MEDIUM"));

        put("AND", CriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("OR", CriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("INTO", CriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("BY", CriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("FROM", CriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("BEGIN", CriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("END", CriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("ELSE", CriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("THEN", CriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("AS", CriticalClassWeight.keywordClassToWeight.get("LOW"));
    }};
}
