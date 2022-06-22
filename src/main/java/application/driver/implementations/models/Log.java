package application.driver.implementations.models;

import application.driver.interfaces.ILog;
import application.driver.interfaces.constants.RequestPropertyIndex;
import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpRequest;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.*;

public class Log implements ILog {


    private String requestTimestamp;

    private String ipSrcAddress;

    private String method;

    private String requestUri;

    private String httpVersion;

    private Map<String, String> queryParameters;

    private Map<String, String> bodyParameters;

    private Map<String, String> headers;


    public Log(String aStringLog)
    {
        List<String> logChunks = List.of(aStringLog.split("\\t"));

        setRequestTimestamp(logChunks.get(0));
        setHttpRequest(aStringLog);
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }

    @Override
    public Map<String, String> getBodyParameters() {
        return bodyParameters;
    }

    @Override
    public String getSrcIPAddress() {
        return ipSrcAddress;
    }

    private void setRequestTimestamp(String aTimestamp) {
        requestTimestamp = aTimestamp;

    }

    private void setHttpRequest(String aStringLog) {

        List<String> requestChunks = List.of(aStringLog.split("\\t"));

//        requestTimestamp = ...

        ipSrcAddress = requestChunks.get(RequestPropertyIndex.IPSRCADDRESS.getIdx());

        setFirstLine(requestChunks.get(RequestPropertyIndex.REQUEST_LINE.getIdx()));
        setQueryParameters();

        setHeaders(requestChunks.get(RequestPropertyIndex.HEADERS.getIdx()));

        if (headers.containsKey("Content-Length"))
        {
            setBodyParameters(requestChunks.get(requestChunks.size()-1));
        }
    }

    private void setFirstLine(String aFirstLine)
    {
        List<String> firstLineChunks =  List.of(aFirstLine.translateEscapes().trim().split(" "));

        method = firstLineChunks.get(0);
        requestUri = firstLineChunks.get(1);
        httpVersion = firstLineChunks.get(2);
    }

    private void setHeaders(String aStringOfHeaders)
    {
        List<String> headerPairs = List.of(aStringOfHeaders.translateEscapes().split("\n"));

        headers = new HashMap<>();

        headerPairs.forEach(headerPair ->
                headers.put(headerPair.split(": ")[0].trim(), headerPair.split(": ")[1].trim()));

    }

    private void setBodyParameters(String aBodyContent)
    {
        List<String> parameterPairs = List.of(aBodyContent.split("&"));

        bodyParameters = new HashMap<>();

        parameterPairs.forEach(parameterPair -> {
            List<String> paramPair = new ArrayList<>(List.of(parameterPair.split("=")));
            if (paramPair.size() == 1)
                paramPair.add("");
            bodyParameters.put(paramPair.get(0), paramPair.get(1));

        });
    }

    private void setQueryParameters()
    {
        if(requestUri.contains("?"))
        {
            List<String> parameterPairs = List.of(requestUri.split("\\?"));

            parameterPairs.forEach(parameterPair ->
                    queryParameters.put(parameterPair.split("=")[0], parameterPair.split("=")[1]));
        }

        else {
            queryParameters = Map.of();
        }
    }
}
