package application.driver.implementations.models;

import application.driver.exceptions.ApplicationException;
import application.driver.exceptions.ApplicationExceptionCauseEnum;
import application.driver.interfaces.ILog;
import application.driver.interfaces.constants.RequestPropertyIndex;
import core.exceptions.CoreException;
import core.exceptions.CoreExceptionCauseEnum;
import core.implementations.models.HTTPRequestParameter;
import core.interfaces.IParameter;

import java.util.*;

public class Log implements ILog {

    private String requestTimestamp;

    private String ipSrcAddress;

    private String method;

    private String requestUri;

    private String httpVersion;

    private String logString;

    private List<IParameter> queryParameters;

    private List<IParameter> bodyParameters;

    private List<IParameter> headers;

    public Log(String aStringLog)
    {

        try {

            logString = aStringLog;

            List<String> logChunks = List.of(aStringLog.split("\\t"));

            setRequestTimestamp(logChunks.get(0));
            setHttpRequest(aStringLog);
        }

        catch (Exception e)
        {
            throw new ApplicationException(ApplicationExceptionCauseEnum.EXCEPTION_AT_PARSING_LOG);
        }

    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public List<IParameter> getHeaders() {
        return headers;
    }

    @Override
    public List<IParameter> getQueryParameters() {
        return queryParameters;
    }

    @Override
    public Optional<List<IParameter>> getBodyParameters() {
        return Optional.ofNullable(bodyParameters);
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

        ipSrcAddress = requestChunks.get(RequestPropertyIndex.IPSRCADDRESS.getIdx());

        setFirstLine(requestChunks.get(RequestPropertyIndex.REQUEST_LINE.getIdx()));

        setQueryParameters();

        setHeaders(requestChunks.get(RequestPropertyIndex.HEADERS.getIdx()));


        try {

            setBodyParameters(requestChunks.get(RequestPropertyIndex.BODY.getIdx()));
        }

        catch (Exception ignored)
        {
        }

    }

    private void setFirstLine(String aFirstLine)
    {
        List<String> firstLineChunks =  List.of(aFirstLine.translateEscapes().trim().split(" "));

        setMethod(firstLineChunks.get(0));
        requestUri = firstLineChunks.get(1);
        httpVersion = firstLineChunks.get(2);
    }

    private void setHeaders(String aStringOfHeaders)
    {
        List<String> headerPairs = List.of(aStringOfHeaders.translateEscapes().split("\n"));

        headers = new ArrayList<>();

        headerPairs.forEach(headerPair -> {

                headers.add(new HTTPRequestParameter(headerPair.split(": ")[0].trim(), headerPair.split(": ")[1].trim()));
        });

    }

    private void setBodyParameters(String aBodyContent)
    {


        if (headers.stream().anyMatch( header -> header.getName().equals("Content-Length"))) {
            List<String> parameterPairs = List.of(aBodyContent.split("&"));

            bodyParameters = new ArrayList<>();

            parameterPairs.forEach(parameterPair -> {
                List<String> paramPair = new ArrayList<>(List.of(parameterPair.split("=")));
                if (paramPair.size() == 1)
                    paramPair.add("");
                bodyParameters.add(new HTTPRequestParameter(paramPair.get(0), paramPair.get(1)));

            });
        }

    }

    private void setQueryParameters()
    {

        if(requestUri.contains("?") && requestUri.contains("="))
        {
            if ( queryParameters == null )
            {
                queryParameters = new ArrayList<>();
            }

            List<String> targetPath_queryParameters = List.of(requestUri.split("\\?"));

            List<String> parameterPairs = List.of(targetPath_queryParameters.get(1).split("&"));

            parameterPairs.forEach(parameterPair ->
            {
                List<String> paramPair = new ArrayList<>(List.of(parameterPair.split("=")));
                if (paramPair.size() == 1)
                    paramPair.add("");
                queryParameters.add(new HTTPRequestParameter(paramPair.get(0), paramPair.get(1)));
            });
        }

        else {
            queryParameters = List.of();
        }
    }

    private void setMethod(String aMethod)
    {
        if (aMethod.equalsIgnoreCase("POST") || aMethod.equalsIgnoreCase("GET"))
        {
            method = aMethod;
        }

        else throw new CoreException(CoreExceptionCauseEnum.METHOD_NOT_SUPPORTED);
    }

    @Override
    public String getTimeOfRequest() {
        return requestTimestamp;
    }

    @Override
    public String getLogAsString() {
        return logString;
    }
}
