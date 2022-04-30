package application.io.logprovider;

import application.Main;
import core.clusterer.ApacheLog;
import core.exceptions.ClusterPhaseRuntimeException;
import core.exceptions.ExceptionCauseEnum;
import nl.basjes.parse.core.Parser;
import nl.basjes.parse.core.exceptions.DissectionFailure;
import nl.basjes.parse.core.exceptions.InvalidDissectorException;
import nl.basjes.parse.core.exceptions.MissingDissectorsException;
import nl.basjes.parse.httpdlog.HttpdLoglineParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ApacheLogProvider {
    private final String pathToFile;

    private final String logFormat;

    private final Parser<ApacheLog> apacheLogParser;

    public ApacheLogProvider(String aPath, String aLogFormat) {

        pathToFile = aPath;

        logFormat = aLogFormat;

        apacheLogParser = new HttpdLoglineParser<ApacheLog>(ApacheLog.class, logFormat);
    }

    public List<ApacheLog> getLogs()
    {

        List<ApacheLog> apacheLogs = new ArrayList<>();

        try (InputStreamReader streamReader = new InputStreamReader(getFileFromResourceAsStream(pathToFile),
                                                                    StandardCharsets.UTF_8);

             BufferedReader reader = new BufferedReader(streamReader)) {

                String LogClass = "";
                String line;
                int i = 1;
                while ((line = reader.readLine()) != null)
                {
                    if(line.length() == 1) // temporary if structure, useful for vizualization
                    {
                        LogClass = line;
                        System.out.println("Found class " + line); // 1 - attack logs, 2 - legit access logs
                        continue;
                    }

                    ApacheLog apacheLog = apacheLogParser.parse(line);
                    apacheLog.LogClass = LogClass;
                    apacheLog.logIndexInFile= i;
                    apacheLogs.add(apacheLog);
                    i++;
                }

        } catch (IOException | DissectionFailure | InvalidDissectorException | MissingDissectorsException e) {
            e.printStackTrace();
            throw new ClusterPhaseRuntimeException(ExceptionCauseEnum.PARSING_APACHE_LOG_EXCEPTION);
        }

        return apacheLogs;
    }

    private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = Main.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new ClusterPhaseRuntimeException(ExceptionCauseEnum.LOG_FILE_NOT_FOUND);
        } else {
            return inputStream;
        }

    }
 }
