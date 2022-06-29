package application.io.logprovider;

import application.Main;
import core.clusterer.acceslogtype.ApacheLog;
import core.exceptions.CoreException;
import core.exceptions.CoreExceptionCauseEnum;
import nl.basjes.parse.core.Parser;
import nl.basjes.parse.core.exceptions.DissectionFailure;
import nl.basjes.parse.core.exceptions.InvalidDissectorException;
import nl.basjes.parse.core.exceptions.MissingDissectorsException;
import nl.basjes.parse.httpdlog.HttpdLoglineParser;

import java.io.*;
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
            throw new CoreException(CoreExceptionCauseEnum.PARSING_APACHE_LOG_EXCEPTION);
        }

        return apacheLogs;
    }

    private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = Main.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new CoreException(CoreExceptionCauseEnum.TRAIN_LOG_FILE_NOT_FOUND);
        } else {
            return inputStream;
        }

    }
 }




