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

        put("(\\b|\\(|\\s|N)CHAR(\\b|\\()", WeightClassEnum.MEDIUM); // CHAR
        put("(\\(|\\s)IFNULL(\\()", WeightClassEnum.MEDIUM); // IFNULL
        put("(\\s+|\\(|;|'|^)+\\bDELETE\\b", WeightClassEnum.MEDIUM); // DELETE
        put("\\bINSERT\\s+", WeightClassEnum.MEDIUM);
        put("\\sCOUNT\\(", WeightClassEnum.MEDIUM);
        put("\\bDROP\\s+(table|database)", WeightClassEnum.MEDIUM);
        put("(\\(|\\s)SLEEP(\\()", WeightClassEnum.MEDIUM);
        put("(\\s|\\()AND\\s+(\\(|'|\\\"|\\d{2,}=)", WeightClassEnum.MEDIUM);
        put("(\\s|\\()OR\\s+(\\(|'|\\\"|\\d{2,})", WeightClassEnum.MEDIUM);
        put("\\(CASE\\s", WeightClassEnum.MEDIUM);
        put("(\\(|\\|\\|)CHR\\(", WeightClassEnum.MEDIUM); // CHR

        put("\\sGROUP BY\\s", WeightClassEnum.LOW);
        put("\\bLIKE\\(", WeightClassEnum.LOW);
        put("\\bEND(|\\)|\\s|--)", WeightClassEnum.LOW);
        put("\\bWHERE\\b", WeightClassEnum.LOW);
        put("(\\s)FROM(\\s|\\()", WeightClassEnum.LOW);
    }};
}
