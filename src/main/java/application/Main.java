package application;

import application.io.logprovider.ApacheLogProvider;
import core.clusterer.acceslogtype.ApacheLog;
import core.clusterer.acceslogtype.EuclideanDistanceMeasure;
import core.exceptions.ClusterPhaseRuntimeException;
import core.exceptions.CoreExceptionCauseEnum;
import core.interfaces.ILogPoint;
import core.transformers.LogToClusterPointMapper;
import nl.basjes.parse.core.exceptions.DissectionFailure;
import nl.basjes.parse.core.exceptions.InvalidDissectorException;
import nl.basjes.parse.core.exceptions.MissingDissectorsException;
import nl.basjes.parse.httpdlog.HttpdLoglineParser;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {

        String filePath = "logfiles/legit_attack_logs.txt";
//        String filePath = "logfiles\\test.txt";
        String logFormat = "%h %l %u %t \"%r\" %>s %b \"%{Referer}i\" \"%{User-Agent}i\"";
        var apacheLogParser = new HttpdLoglineParser<ApacheLog>(ApacheLog.class, logFormat);

        try (InputStreamReader streamReader = new InputStreamReader(getFileFromResourceAsStream(filePath),
                StandardCharsets.UTF_8);

             BufferedReader reader = new BufferedReader(streamReader)) {
            String newLine = System.getProperty("line.separator");

            String LogClass = "";
            String line;
            int i = 1;
            while ((line = reader.readLine()) != null)
            {
                if(line.length() == 1) // temporary if structure, useful for vizualization
                {
                    LogClass = line;
                    FileWriter myWriter = new FileWriter("C:\\Users\\ioana.dobos\\Desktop\\onboarding-projects\\Licenta\\SQLiDetectorForWebservers\\src\\main\\resources\\logfiles\\apachetotsharklogs.txt", true);
                    myWriter.append(line+newLine);
                    myWriter.close();

                    continue;
                }

                ApacheLog apacheLog = apacheLogParser.parse(line);
                i++;

                try {
                    FileWriter myWriter = new FileWriter("C:\\Users\\ioana.dobos\\Desktop\\onboarding-projects\\Licenta\\SQLiDetectorForWebservers\\src\\main\\resources\\logfiles\\apachetotsharklogs.txt", true);

                    StringBuilder headersBuilder = new StringBuilder();
                    apacheLog.getHeaders().forEach(header ->
                    {
                        headersBuilder.append(header.getName()+ ": " + header.getValue() + "\\r\\n ");
                    });

                    myWriter.append(apacheLog.getTimeOfRequest() + "\t" + apacheLog.getSrcIPAddress() + "\t" +
                            apacheLog.getFirstLine() + "\\r\\n\t" + headersBuilder.toString() + newLine );
                    myWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }

        } catch (IOException | DissectionFailure | InvalidDissectorException | MissingDissectorsException e) {
            e.printStackTrace();
            throw new ClusterPhaseRuntimeException(CoreExceptionCauseEnum.PARSING_APACHE_LOG_EXCEPTION);
        }



/*        var apacheLogPoints = loadLogs();

        List<Cluster<ILogPoint>> clusters = clusterLogPoints(2, 10, apacheLogPoints);

        ClassifiedLogPointsMapProvider.loadClassifiedLogPointsMapFromClustererResult(clusters);


        Object ExceptionReasonEnum;
        if (args.length == 1 && (Objects.equals(args[0], "--console") || Objects.equals(args[0], "--server"))) {
            // TODO : switch DI according to command line argument
            IDriverClass applicationDriver = new Driver(new ConsoleInputServiceImpl(), new ConsoleOutputServiceImpl());

            applicationDriver.start();

        } else throw new ApplicationException(ApplicationExceptionCauseEnum.INVALID_ARGUMENTS);
*/
    }

    private static List<ILogPoint> loadLogs()
    {
        String filePath = "logfiles/legit_attack_logs.txt";
        String logformat = "%h %l %u %t \"%r\" %>s %b \"%{Referer}i\" \"%{User-Agent}i\"";

        ApacheLogProvider logProvider = new ApacheLogProvider(filePath, logformat);

        return  logProvider.getLogs().stream().map(LogToClusterPointMapper::fromApacheLog).collect(Collectors.toList());
    }

    private static List<Cluster<ILogPoint>> clusterLogPoints(double eps, int minPts, List<ILogPoint> apacheLogPoints)
    {
        EuclideanDistanceMeasure euclideanDistanceMeasure = new EuclideanDistanceMeasure();

        DBSCANClusterer<ILogPoint> clusterer = new DBSCANClusterer<>(eps, minPts, euclideanDistanceMeasure);

        return clusterer.cluster(apacheLogPoints);
    }
    private static final List<Color> colorList = new ArrayList<>(){{
        add(Color.ORANGE);
        add(Color.MAGENTA);
        add(Color.CYAN);
        add(Color.BLACK);
        add(Color.BLUE);
        add(Color.PINK);
        add(Color.YELLOW);
        add(Color.GREEN);
    }};

    private static final Map<Integer, String> indexToClusterNameMap = new HashMap<>()
    {{
        put(0, "Cereri legitime");
        put(1, "Cereri care contin un posibil atac");
        put(2, "Cereri de atac");
    }};


    private static InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = Main.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new ClusterPhaseRuntimeException(CoreExceptionCauseEnum.LOG_FILE_NOT_FOUND);
        } else {
            return inputStream;
        }

    }
}
