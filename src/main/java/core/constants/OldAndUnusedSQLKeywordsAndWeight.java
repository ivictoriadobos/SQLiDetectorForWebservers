package core.constants;

import java.util.HashMap;
import java.util.Map;

public class OldAndUnusedSQLKeywordsAndWeight {

    public static Map<String, Double> keywordsAndWeight = new HashMap<String, Double>() {{
        put("\\s+UNION\\s+", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("TRUNCATE[\\s\\(]+", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("EXEC[\\s\\(]+", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("SELECT[\\s\\(]+", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("DUAL", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("SYSDATE", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("DBMS", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("RLIKE", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("MAKE_SET", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("ELT", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("HIGH"));
        put("NVL[\\s\\(]+", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("HIGH"));

        put("TABLE", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("DELETE", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("UPDATE", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("INSERT\\s+", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("COUNT[\\s\\(]+", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("WHERE", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("GROUP BY", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("ORDER BY\\s+", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("DROP\\s+(table|database)", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("SLEEP\\s+", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("CASE", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("LIKE\\s+", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("MEDIUM"));
        put("(\\s|\\()AND\\s+(\\(|'|\\\"|\\d{2,}=)", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("LOW"));

        put("\\s+OR\\s+", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("\\s+INTO\\s+", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("\\s+FROM\\s+", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("BEGIN\\s+", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("END", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("ELSE", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("THEN", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("LOW"));
        put("\\s+AS\\s+", OldAndUnusedCriticalClassWeight.keywordClassToWeight.get("LOW"));
    }};
}
