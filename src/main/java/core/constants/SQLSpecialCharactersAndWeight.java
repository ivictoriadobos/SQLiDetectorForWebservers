package core.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLSpecialCharactersAndWeight {
    public static Map<String, WeightClassEnum> specialCharacters = new HashMap<>()
    {{
        put("(\\)|\\w|;)+--(\\s)*", WeightClassEnum.CRITICAL); // --
        put("(\\)|\\\")+>(\\(|\\)|\\d{2,})", WeightClassEnum.CRITICAL); // >
        put("(\\d{3,}|'|\\\"|!|\\))=(\\d{3,}|\\s|CAST|'|CONVERT|\\\"|CONCAT|\\()", WeightClassEnum.CRITICAL); // =
        put("([A-Za-z0-9]+\\s*)=+\\s*(?=\\1)", WeightClassEnum.CRITICAL); // identitate
        put("(\\)|\\d{3,}),(\\(|CHAR|CHR|CONCAT|\\d{1}x)", WeightClassEnum.CRITICAL);// ,
        put("\\);", WeightClassEnum.CRITICAL);
        put("(\\(|\\)|\\s|\\d{3,})\\*(\\s|\\(|\\)|\\d)+", WeightClassEnum.HIGH); // *

        put("(\\)|')+\\+(CHAR|\\(|')+", WeightClassEnum.HIGH); // +
        put("(\\)|'|\\\");(CREATE|WAITFOR|DECLARE|SET|SELECT|IF|EXEC|\\()", WeightClassEnum.HIGH);

        put("'", WeightClassEnum.LOW);
        put("\\\"", WeightClassEnum.LOW); // " -> \\ \" -> "
    }};

    public static Map<String, WeightClassEnum> restrictedSpecialCharacters = new HashMap<>()
    {{
        put("(\\(|[a-zA-Z]|\\d{3,}|'|;|\\))--(\\s|$)*", WeightClassEnum.CRITICAL); //--

        put("(\\d{3,}|'|\\\"|!|\\))=(\\d{3,}|CAST|'|CONVERT|\\\"|CONCAT|\\()", WeightClassEnum.CRITICAL);// =
        put("(\\)|\\\")+>(\\(|\\)|\\d{2,})", WeightClassEnum.CRITICAL);
        put("([A-Za-z0-9]+\\s*)=+\\s*(?=\\1)", WeightClassEnum.CRITICAL); // identitate
        put("(\\)|\\d{3,}),(\\(|\\s|CHAR|CHR|CONCAT|\\d{1}x)", WeightClassEnum.CRITICAL);// ,

        put("(\\)|')+\\+(CHAR|\\(|')+", WeightClassEnum.HIGH); // +

        put("(\\(|\\)|\\s|\\d{3,})\\*(\\s|\\(|\\)|\\d)+", WeightClassEnum.MEDIUM); // *

        put("'", WeightClassEnum.EXTRA_LOW);
    }};
}
