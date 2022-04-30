package application;

import application.io.logprovider.ApacheLogProvider;
import core.clusterer.ApacheDistanceMetric;
import core.clusterer.ApacheLog;
import core.clusterer.ApacheLogPoint;
import core.transformers.LogToClusterPointMapper;
import graph.*;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    private static List<Color> colorList = new ArrayList<>(){{
        add(Color.ORANGE);
        add(Color.MAGENTA);
        add(Color.CYAN);
        add(Color.BLACK);
        add(Color.BLUE);
        add(Color.PINK);
        add(Color.YELLOW);
    }};

    public static void main(String[] args) throws IOException {

//        String filePath  = "logfiles/test-access.log";
        String filePath  = "logfiles/attack-logs.log";
        String logformat = "%h %l %u %t \"%r\" %>s %b \"%{Referer}i\" \"%{User-Agent}i\"";

        ApacheLogProvider logProvider = new ApacheLogProvider(filePath, logformat);

        List<ApacheLogPoint> logPoints = logProvider.getLogs().stream().map(LogToClusterPointMapper::fromApacheLog).collect(Collectors.toList());

        SimpleGraph simpleGraph = new SimpleGraph();
        simpleGraph.display();
        for (ApacheLogPoint apacheLogPoint: logPoints)
        {
            if (Objects.equals(apacheLogPoint.LogClass, "1"))
            {
                simpleGraph.addPoint(apacheLogPoint.Score[0], apacheLogPoint.Score[1], Color.RED);
            }
            else
            {
                simpleGraph.addPoint(apacheLogPoint.Score[0], apacheLogPoint.Score[1], Color.GREEN);
                if (apacheLogPoint.Score[0] > 110)
                {
                    System.out.println("Wtf is wrong with u bro");
                }
            }
        }
        simpleGraph.setGridSpreadX(20);
        simpleGraph.setGridSpreadY(20);
        simpleGraph.repaint();


        double eps = 5;
        int minPts = 5;
        ApacheDistanceMetric distanceMetric = new ApacheDistanceMetric();

        DBSCANClusterer<ApacheLogPoint> clusterer = new DBSCANClusterer<>(eps, minPts, distanceMetric);

        List<Cluster<ApacheLogPoint>> clusters = clusterer.cluster(logPoints);

        SimpleGraph clusteredPointsGraph = new SimpleGraph();
        clusteredPointsGraph.display();
        clusteredPointsGraph.setGridSpreadX(20);
        clusteredPointsGraph.setGridSpreadY(20);

        int i = 0;
        int numberOfClusteredPoints = 0;
        for( Cluster<ApacheLogPoint> cluster: clusters)
        {
            List<ApacheLogPoint> points = cluster.getPoints();
            numberOfClusteredPoints += points.size();
            for (ApacheLogPoint apacheLogPoint : points)
            {
                clusteredPointsGraph.addPoint(apacheLogPoint.Score[0], apacheLogPoint.Score[1], colorList.get(i));
            }
            i++;
        }

        clusteredPointsGraph.repaint();
        System.out.println("Clusters found: " + clusters.size());

        System.out.println("Number of unclustered and lost points: " + (logPoints.size() - numberOfClusteredPoints));
    }

    private static void buildGraph()
    {

    }

}
