package core.constants;

import java.util.ArrayList;
import java.util.List;

public class SQLSpecialCharacters {
    public static List<String> specialCharacters = new ArrayList<>()
    {{
        add("\\+");
        add("-");
        add("\\*");
        add("\\/");
        add("=");
        add("!=");
        add("<>");
        add("\\)");
        add("\\(");
        add("\\/\\*"); // /*
        add("\\*\\\\"); // *\
        add("'");
        add("\"");
        add(">=");
        add("<=");
        add(",");
    }};
}
