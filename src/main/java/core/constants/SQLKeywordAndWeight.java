package core.constants;

import java.util.HashMap;
import java.util.Map;

public class SQLKeywordAndWeight {

    public static Map<String, WeightClassEnum> keywordsAndWeight = new HashMap<String, WeightClassEnum>() {{
        put("(\\w+\\s+|^|')UNION\\s+", WeightClassEnum.CRITICAL); // UNION
        put("(\\s+|\\(|;|'|^)+\\bEXEC\\s+", WeightClassEnum.CRITICAL); // EXEC
        put("FROM\\sDUAL(--|\\s|\\)|&|;|$)+", WeightClassEnum.CRITICAL); // FROM DUAL
        put("(\\s|'|;|^)+DBMS_(LOCK|UTILITY||PIPE)", WeightClassEnum.CRITICAL); // DBMS_LOCK|UTILITY|PIPE
        put("\\sRLIKE(\\s|\\(|')+", WeightClassEnum.CRITICAL); // RLIKE
        put("(\\s|^)+MAKE_SET\\(", WeightClassEnum.CRITICAL); // MAKE_SET
        put("(\\s|\\(|^)+ELT\\(", WeightClassEnum.CRITICAL); // ELT
        put("(,|\\s|\\(|=)+CONCAT\\(", WeightClassEnum.CRITICAL); // CONCAT
        put("(\\s|\\(|,)+EXTRACTVALUE\\(", WeightClassEnum.CRITICAL); // EXTRACTVALUE
        put("\\s+INFORMATION_SCHEMA.(PLUGINS|SCHEMATA|TABLES|INNODB_LOCKS|COLUMNS|SESSION_STATUS|USER_PRIVILEGES)+", WeightClassEnum.CRITICAL);
        put("\\s+TABLE_SCHEMA=", WeightClassEnum.CRITICAL);

        put("(\\s+|\\(|;|'|^)+\\bSELECT(\\s|\\()", WeightClassEnum.HIGH); // SELECT
        put("(\\(|\\s|,|^|;)+UPDATEXML\\(", WeightClassEnum.HIGH); // UPDATEXML

        put("(\\s+|\\(|;|'|^)+\\bDELETE\\b", WeightClassEnum.MEDIUM); // DE;ETE
        put("INSERT\\s+", WeightClassEnum.MEDIUM);
        put("COUNT[\\s\\(]+", WeightClassEnum.MEDIUM);
        put("GROUP BY", WeightClassEnum.MEDIUM);
        put("ORDER BY[\\s\\(]+", WeightClassEnum.MEDIUM);
        put("DROP\\s+(table|database)", WeightClassEnum.MEDIUM);
        put("CASE", WeightClassEnum.MEDIUM);
        put("SLEEP\\s+", WeightClassEnum.MEDIUM);

        put("LIKE\\s+", WeightClassEnum.MEDIUM);
        put("\\s+WHERE EXISTS\\s+", WeightClassEnum.MEDIUM);
        put("WHERE", WeightClassEnum.MEDIUM);
        put("\\s+INTO\\s+", WeightClassEnum.LOW);
        put("BEGIN\\s+", WeightClassEnum.LOW);
        put("\\s+FROM\\s+", WeightClassEnum.EXTRA_LOW);
        put("END", WeightClassEnum.EXTRA_LOW);
        put("ELSE", WeightClassEnum.EXTRA_LOW);
        put("THEN", WeightClassEnum.EXTRA_LOW);
        put("\\s+AS\\s+", WeightClassEnum.EXTRA_LOW);
        put("\\s+OR\\s+", WeightClassEnum.EXTRA_LOW);
        put("\\s+AND\\s+", WeightClassEnum.EXTRA_LOW);
    }};
}
