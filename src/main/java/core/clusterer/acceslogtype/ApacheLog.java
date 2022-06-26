package core.clusterer.acceslogtype;
import application.driver.interfaces.ILog;
import core.constants.SQLKeywordAndWeight;
import core.constants.SQLSpecialCharacters;
import core.constants.WeightClassEnum;
import core.interfaces.IParameter;
import core.transformers.UrlDecoder;
import nl.basjes.parse.core.Field;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApacheLog implements ILog {

    public String LogClass = "";
    public int logIndexInFile = -1;
    private String clientIP;

    private String timeOfRequest;

    private String httpMethod;

    private String originalUriQuery = "";

    private final Map<String, String> uriParams = new HashMap<>(32);
    private String target;

    private String originalUri;

    private String statusCode;

    private String userAgent = "";

    private String referer;

    private String sizeInBytes;

    public ApacheLog() {
        clear();
    }


    public void clear()
    {
        sizeInBytes = referer = userAgent = statusCode = originalUri = target = originalUriQuery = httpMethod = timeOfRequest = clientIP = "";
        uriParams.clear();
    }

    public double getWeightedSumOfSpecialCharacters()
    {
        double specialCharactersKeywordsScore = 0;

        for (Map.Entry<String, WeightClassEnum> mapEntry : SQLSpecialCharacters.specialCharacters.entrySet()) {

            specialCharactersKeywordsScore+= regexMatchSequenceOverRequestUri(mapEntry.getKey()) * mapEntry.getValue().getValue();
        }

        for (Map.Entry<String, WeightClassEnum> mapEntry : SQLSpecialCharacters.specialCharactersForUserAgent.entrySet()) {

            specialCharactersKeywordsScore+= regexMatchSequenceOverUserAgent(mapEntry.getKey()) * mapEntry.getValue().getValue();
        }

        return specialCharactersKeywordsScore;
    }

    public double getWeightedSumOfSQLKeywords()
    {
        double sqlKeywordsScore = 0;

        for (Map.Entry<String, WeightClassEnum> mapEntry : SQLKeywordAndWeight.keywordsAndWeight.entrySet()) {

            sqlKeywordsScore+= regexMatchSequenceOverRequestUri(mapEntry.getKey()) * mapEntry.getValue().getValue()
                                + regexMatchSequenceOverUserAgent(mapEntry.getKey()) * mapEntry.getValue().getValue();
        }

        return sqlKeywordsScore;
    }

    public int getNumberOfSpecialCharacters()
    {
        return SQLSpecialCharacters.specialCharacters.keySet().stream()
                .mapToInt(this::regexMatchSequenceOverRequestUri)
                .sum() +

                SQLSpecialCharacters.specialCharactersForUserAgent.keySet().stream()
                .mapToInt(this::regexMatchSequenceOverUserAgent)
                .sum();
    }

    public int getNumberOfSQLKeywords()
    {
        int noOfSQLKeywordsContained = 0;

        noOfSQLKeywordsContained = SQLKeywordAndWeight.keywordsAndWeight.keySet().stream()
                                        .mapToInt(this::regexMatchSequenceOverRequestUri)
                                        .sum()
                                    + SQLKeywordAndWeight.keywordsAndWeight.keySet().stream()
                                        .mapToInt(this::regexMatchSequenceOverUserAgent)
                                        .sum();

        return noOfSQLKeywordsContained;
    }
    private int regexMatchSequenceOverRequestUri(final String sequence)
    {
        if (originalUriQuery.length() == 0)
            return 0;

        Pattern sequenceRegexPattern = Pattern.compile(sequence);

        Matcher matcher = sequenceRegexPattern.matcher(originalUriQuery);

        return (int) matcher.results().count();
    }

    private int regexMatchSequenceOverUserAgent(final String sequence)
    {
        if(userAgent == null)
        {
            userAgent = "";
            System.out.println("wut");
        }
        if (userAgent.length() == 0)
            return 0;

        Pattern sequenceRegexPattern = Pattern.compile(sequence);
        Matcher matcher = sequenceRegexPattern.matcher(userAgent);

        return (int) matcher.results().count();
    }

    public int findOccurrencesNumberOfWhiteSpaces()
    {
        if (originalUriQuery.length() == 0)
            return 0;

        int numberOfSpecialCharacters = 0;

        Pattern sequenceRegexPattern = Pattern.compile("\\s");

        Matcher matcher = sequenceRegexPattern.matcher(originalUriQuery);

        numberOfSpecialCharacters += matcher.results().count();

        return numberOfSpecialCharacters;
    }

    @Field("STRING:request.firstline.original.uri.query.*")
    public void setUriParams(final String name, final String value)
    {
        uriParams.put(name, value);
    }

    @Field("TIME.STAMP:request.receive.time")
    public void setTimeOfRequest(final String value)
    {
        timeOfRequest = value;
    }

    @Field("HTTP.METHOD:request.firstline.method")
    public void setHttpMethod(final String value)
    {
        httpMethod = value;
    }

    @Field("HTTP.URI:request.firstline.original.uri")
    public void setOriginalUri(final String value)
    {
        originalUri = value;
    }

    @Field("HTTP.PATH:request.firstline.original.uri.path")
    public void setTarget(final String value)
    {
        target = value;
    }

    @Field("HTTP.QUERYSTRING:request.firstline.original.uri.query")
    public void setOriginalUriQuery(final String value)
    {
        originalUriQuery = value;

        originalUriQuery = originalUriQuery.toUpperCase();

        decode();
    }

    @Field("IP:connection.client.host")
    public void setClientIP(final String value)
    {
        clientIP = value;
    }

    @Field("HTTP.URI:request.referer")
    public void setReferer(final String value)
    {
        referer = value;
    }

    @Field("STRING:request.status.last")
    public void setStatusCode(final String value)
    {
        statusCode = value;
    }

    @Field("BYTES:response.body.bytes")
    public void setSizeInBytes(final String value)
    {
        sizeInBytes = value;
    }

    @Field("HTTP.USERAGENT:request.user-agent")
    public void setUserAgent(final String value)
    {
        userAgent = value;
    }

    public String getOriginalUriQuery() {
        return originalUriQuery;
    }

    public int getPayloadLength()
    {
        return Optional.of(originalUriQuery.length()).orElse(0);
    }

    private void decode()
    {
        try {
            originalUriQuery = UrlDecoder.decode(originalUriQuery);
        }
        catch (Exception e)
        {
//            e.printStackTrace(); weird parse exception we get even though there's no problem at parsing the respective log
        }
    }

    @Override
    public String getMethod() {
        return httpMethod;
    }

    @Override
    public List<IParameter> getHeaders() {
        return null;
    }

    @Override
    public List<IParameter> getQueryParameters() {
        return null;
    }

    @Override
    public Optional<List<IParameter>> getBodyParameters() {
        return null;
    }

    @Override
    public String getSrcIPAddress() {
        return clientIP;
    }
}
