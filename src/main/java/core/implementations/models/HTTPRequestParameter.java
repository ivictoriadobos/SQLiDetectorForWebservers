package core.implementations.models;

import core.constants.SQLKeywordAndWeight;
import core.constants.SQLSpecialCharactersAndWeight;
import core.constants.WeightClassEnum;
import core.exceptions.CoreException;
import core.exceptions.CoreExceptionCauseEnum;
import core.interfaces.IParameter;
import core.transformers.UrlDecoder;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTTPRequestParameter implements IParameter {

    private String name;
    private String value;

    public HTTPRequestParameter(String aName, String aValue ) {

        setName(aName);
        setValue(aValue);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Double getRawAttackScore() {

        var score = 0.0;

        for (Map.Entry<String, WeightClassEnum> entry : SQLKeywordAndWeight.keywordsAndWeight.entrySet()) {

            Pattern regexPattern = Pattern.compile(entry.getKey(), Pattern.CASE_INSENSITIVE);
            Matcher matcher = regexPattern.matcher(value);

            score += matcher.results().count() * entry.getValue().getValue();
        }

        Map<String, WeightClassEnum> specialCharactersMap;
        if (name.equalsIgnoreCase("User-Agent") || name.equalsIgnoreCase("sec-ch-ua") || name.equalsIgnoreCase("Cookie"))
        {
            specialCharactersMap = SQLSpecialCharactersAndWeight.restrictedSpecialCharacters;
        }
        else specialCharactersMap = SQLSpecialCharactersAndWeight.specialCharacters;

        for (Map.Entry<String, WeightClassEnum> entry : specialCharactersMap.entrySet())
        {
            Pattern regexPattern = Pattern.compile(entry.getKey());
            Matcher matcher = regexPattern.matcher(value);

            score += matcher.results().count() * entry.getValue().getValue();
        }

        return score;
    }

    private void setName(String aName)
    {
        if (aName.equals(""))
        {
            throw new CoreException(CoreExceptionCauseEnum.INVALID_PARAMETER_NAME);
        }

        name = UrlDecoder.decode(aName);
    }

    private void setValue(String aValue)
    {
        if(aValue.equals(""))
        {
            throw new CoreException(CoreExceptionCauseEnum.INVALID_PARAMETER_VALUE);
        }

        value = UrlDecoder.decode(aValue);

    }
}
