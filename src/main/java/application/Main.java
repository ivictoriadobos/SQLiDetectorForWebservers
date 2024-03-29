package application;

import application.driver.exceptions.ApplicationException;
import application.driver.exceptions.ApplicationExceptionCauseEnum;
import application.driver.implementations.Driver;
import application.driver.implementations.services.ConsoleInputServiceImpl;
import application.driver.implementations.services.ConsoleOutputServiceImpl;
import application.driver.implementations.services.TailBashCommandInputServiceImpl;
import application.driver.interfaces.IDriverClass;
import application.io.logprovider.ApacheLogProvider;
import application.io.logprovider.ClassifiedLogPointsMapProvider;
import core.clusterer.acceslogtype.EuclideanDistanceMeasure;
import core.exceptions.CoreException;
import core.exceptions.CoreExceptionCauseEnum;
import core.interfaces.ILogPoint;
import core.transformers.LogToClusterPointMapper;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {

        var apacheLogPoints = loadLogs();

        List<Cluster<ILogPoint>> clusters = clusterLogPoints(2, 10, apacheLogPoints);

        ClassifiedLogPointsMapProvider.loadClassifiedLogPointsMapFromClustererResult(clusters);

        if (args.length == 1 && (Objects.equals(args[0], "--console") || Objects.equals(args[0], "--server"))) {

            IDriverClass applicationDriver;

            switch (args[0]) {
                case "--console" -> {
                    applicationDriver = new Driver(new ConsoleInputServiceImpl(), new ConsoleOutputServiceImpl());
                    applicationDriver.start();
                }
                case "--server" -> {
                    applicationDriver = new Driver(new TailBashCommandInputServiceImpl(), new ConsoleOutputServiceImpl());
                    applicationDriver.start();
                }
            }

        }
        else throw new ApplicationException(ApplicationExceptionCauseEnum.INVALID_ARGUMENTS);
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
            throw new CoreException(CoreExceptionCauseEnum.TRAIN_LOG_FILE_NOT_FOUND);
        } else {
            return inputStream;
        }

    }
}
