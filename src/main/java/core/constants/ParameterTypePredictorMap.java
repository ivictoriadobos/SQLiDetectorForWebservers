package core.constants;

import java.util.HashMap;
import java.util.Map;

public class ParameterTypePredictorMap {

    public static Map<String, ParameterClassTypeEnum> map = new HashMap<>()
    {{
        put("username", ParameterClassTypeEnum.NSQL);
        put("user", ParameterClassTypeEnum.NSQL);
        put("email", ParameterClassTypeEnum.NSQL);
        put("pass", ParameterClassTypeEnum.NSQL);
        put("password", ParameterClassTypeEnum.NSQL);
        put("search", ParameterClassTypeEnum.NSQL);
        put("birthday", ParameterClassTypeEnum.NSQL);
        put("year", ParameterClassTypeEnum.NSQL);
        put("age", ParameterClassTypeEnum.NSQL);
        put("title", ParameterClassTypeEnum.NSQL);
        put("comment", ParameterClassTypeEnum.NSQL);
        put("date", ParameterClassTypeEnum.NSQL);
        put("filter", ParameterClassTypeEnum.NSQL);
        put("timestamp", ParameterClassTypeEnum.NSQL);
        put("parameter", ParameterClassTypeEnum.NSQL);


        put("query", ParameterClassTypeEnum.SQL);
        put("sql", ParameterClassTypeEnum.SQL);
        put("code", ParameterClassTypeEnum.SQL);
        put("command", ParameterClassTypeEnum.SQL);
        put("ddl", ParameterClassTypeEnum.SQL);
        put("dml", ParameterClassTypeEnum.SQL);
        put("db", ParameterClassTypeEnum.SQL);
    }};
}
