package application;

import application.io.logprovider.ApacheLogProvider;
import core.ApacheLog;
import core.clusterer.ApacheDistanceMetric;
import core.clusterer.ApacheLogPoint;
import core.transformers.LogToClusterPointMapper;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello");

//        String filePath  = "logfiles/test-access.log";
        String filePath  = "logfiles/attack-logs.log";
        String logformat = "%h %l %u %t \"%r\" %>s %b \"%{Referer}i\" \"%{User-Agent}i\"";

        ApacheLogProvider logProvider = new ApacheLogProvider(filePath, logformat);

        List<ApacheLogPoint> logPoints = logProvider.getLogs().stream().map(LogToClusterPointMapper::fromApacheLog).collect(Collectors.toList());

        double eps = 10;
        int minPts = 5;
        ApacheDistanceMetric distanceMetric = new ApacheDistanceMetric();

        DBSCANClusterer<ApacheLogPoint> clusterer = new DBSCANClusterer<>(eps, minPts, distanceMetric);

        List<Cluster<ApacheLogPoint>> clusters = clusterer.cluster(logPoints);

        System.out.println(clusters.stream().count());
    }

}