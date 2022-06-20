package application.driver.implementations;

import application.driver.interfaces.ILog;
import core.clusterer.acceslogtype.ApacheLog;
import nl.basjes.parse.core.Parser;
import nl.basjes.parse.core.exceptions.DissectionFailure;
import nl.basjes.parse.core.exceptions.InvalidDissectorException;
import nl.basjes.parse.core.exceptions.MissingDissectorsException;
import nl.basjes.parse.httpdlog.HttpdLoglineParser;
import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpRequest;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class Log implements ILog {

    private Map<String, String> headers;

    private Map<String, String> parameters;

    private String method;

    public Log(String aStringLog)
    {
        RawHttpRequest request = new RawHttp().parseRequest(aStringLog);

        try {
            System.out.println(request.eagerly().getBody().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Map<String, String> getHeaders() {
        return null;
    }

    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public Map<String, String> getParameters() {
        return null;
    }
}
