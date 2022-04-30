package core.constants;

import java.util.ArrayList;
import java.util.List;

public class SQLSpecialCharacters {
    public static List<String> specialCharacters = new ArrayList<>()
    {{
        add("(\\)|')+\\+(CHAR|\\(|')+"); // +
        add("(\\)|\\w|;)+--(\\s)*"); // --
        add("(\\(|\\)|\\s|\\d{3,})\\*(\\s|\\(|\\)|\\d)+"); // *
        add("/");
        add("(\\d{3,}|'|\\\"|!|\\))=(\\d{3,}|\\s|CAST|'|CONVERT|\\\"|CONCAT|\\()"); // =
        add("<>");
        add("\\)");
        add("\\(");
        add("'");
        add("\"");
//        add(",");
        add(";");
    }};

    public static List<String> specialCharactersForUserAgent = new ArrayList<>()
    {{
        add("(\\)|')+\\+(CHAR|\\(|')+");
        add("(\\(|[a-zA-Z]|\\d{3,}|'|;|\\))--(\\s|$)*");//--
        add("\\*");
        add("(\\d{3,}|'|\\\"|!|\\))=(\\d{3,}|\\s|CAST|'|CONVERT|\\\"|CONCAT|\\()");
        add("<>");
        add("'");
        add("\"");
    }};
}
