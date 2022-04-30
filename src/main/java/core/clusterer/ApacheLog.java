package core.clusterer;
import core.constants.SQLKeywordAndWeight;
import core.constants.SQLKeywordsAndWeight;
import core.constants.SQLSpecialCharacters;
import core.transformers.UrlDecoder;
import nl.basjes.parse.core.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApacheLog {

    public String LogClass = "";
    public int logIndexInFile = -1;
    private String clientIP;

    private String timeOfRequest;

    private String httpMethod;

    private String originalUriQuery;

    private Map<String, String> uriParams = new HashMap<>(32);
    private String target;

    private String originalUri;

    private String statusCode;

    private String userAgent;

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

    private void decode()
    {
        try {
            originalUriQuery = UrlDecoder.decode(originalUriQuery);
        }
        catch (Exception e)
        {
//            e.printStackTrace();
        }
    }

    public int findOccurrencesNumberOfSpecialCharacters()
    {

        int specialCharactersInRequestUri = SQLSpecialCharacters.specialCharacters.stream()
                                            .mapToInt(this::findOccurrencesNumberInRequestUri)
                                            .sum();

        int specialCharactersInUserAgent = SQLSpecialCharacters.specialCharactersForUserAgent.stream()
                                            .mapToInt(this::findOccurrencesNumberInUserAgent)
                                            .sum();

        return specialCharactersInRequestUri + specialCharactersInUserAgent;
    }

    public int findOccurrencesNumberOfSQLKeywords()
    {

        int sqlKeywordsNumberInRequestUri = SQLKeywordAndWeight.keywordsAndWeight.keySet().stream()
                                            .mapToInt(this::findOccurrencesNumberInRequestUri)
                                            .sum();

        int sqlKeywordsNumberInUserAgent = SQLKeywordAndWeight.keywordsAndWeight.keySet().stream()
                                            .mapToInt(this::findOccurrencesNumberInUserAgent)
                                            .sum();

        return sqlKeywordsNumberInRequestUri + sqlKeywordsNumberInUserAgent;
    }

    public String getOriginalUriQuery() {
        return originalUriQuery;
    }

    private int findOccurrencesNumberInRequestUri(final String sequence)
    {
        if (originalUriQuery.length() == 0)
            return 0;

        int numberOfSpecialCharacters = 0;

        Pattern sequenceRegexPattern = Pattern.compile(sequence);

        Matcher matcher = sequenceRegexPattern.matcher(originalUriQuery);

        numberOfSpecialCharacters += matcher.results().count();

        return numberOfSpecialCharacters;
    }

    private int findOccurrencesNumberInUserAgent(final String sequence)
    {
        if (userAgent.length() == 0)
            return 0;

        int numberOfSpecialCharacters = 0;

        Pattern sequenceRegexPattern = Pattern.compile(sequence);

        Matcher matcher = sequenceRegexPattern.matcher(userAgent);

        numberOfSpecialCharacters += matcher.results().count();

        return numberOfSpecialCharacters;
    }

    public int findOccurrencesNumberOfWhiteSpaces()
    {
        if (originalUriQuery.length() == 0)
            return 0;

        int numberOfSpecialCharacters = 0;

        Pattern sequenceRegexPattern = Pattern.compile(" ");

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

        decode();

        originalUri = originalUri.toLowerCase();
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


    public String getHttpMethod() {
        return httpMethod;
    }

    public int getPayloadLength()
    {
        return Optional.of(originalUriQuery.length()).orElse(0);
    }
}
