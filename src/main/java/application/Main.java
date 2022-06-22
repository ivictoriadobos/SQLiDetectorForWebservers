package application;

import application.io.logprovider.ApacheLogProvider;
import application.io.logprovider.ClassifiedLogPointsMapProvider;
import core.clusterer.acceslogtype.EuclideanDistanceMeasure;
import core.clusterer.acceslogtype.ApacheLogPoint;
import core.interfaces.ILogPoint;
import core.transformers.LogToClusterPointMapper;
import graph.*;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
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

/*
        if (args.length == 1 && (Objects.equals(args[0], "--console") || Objects.equals(args[0], "--server"))) {
            // TODO : switch DI according to command line argument
            IDriverClass applicationDriver = new Driver(new ConsoleInputServiceImpl(), new ConsoleOutputServiceImpl());

            applicationDriver.start();

        } else throw new ApplicationException(ExceptionReasonEnum.INVALID_ARGUMENTS);
*/
    }



    private static XYChart buildXYChart(XYSeries.XYSeriesRenderStyle chartStyle, List<Double> xSeries, List<Double> ySeries, String seriesName )
    {
        XYChart chart = new XYChart(1200, 1600);
        chart.getStyler().setDefaultSeriesRenderStyle(chartStyle);

        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
        chart.getStyler().setMarkerSize(16);
        chart.addSeries(seriesName, xSeries, ySeries);
//        new SwingWrapper<XYChart>(chart).displayChart();

        return chart;
    }

    private static void buildGraph()
    {
        SimpleGraph simpleGraphLegitAccess = new SimpleGraph();
        simpleGraphLegitAccess.display();

        simpleGraphLegitAccess.setGridSpreadX(20);
        simpleGraphLegitAccess.setGridSpreadY(20);
    }

    private static List<ILogPoint> loadLogs()
    {
        String filePath = "logfiles/legit_attack_logs.txt";
        String logformat = "%h %l %u %t \"%r\" %>s %b \"%{Referer}i\" \"%{User-Agent}i\"";

        ApacheLogProvider logProvider = new ApacheLogProvider(filePath, logformat);

        return  logProvider.getLogs().stream().map(LogToClusterPointMapper::fromApacheLog).collect(Collectors.toList());
    }

    private static void rawScoreToFrequencyInLogsGraph(List<ApacheLogPoint> apacheLogPoints)
    {
        Map<Double, Double> scoreToNumberOfAppearences = new HashMap<>()
        {{
            put(0.0, 0.0);
            put(1.0, 0.0);
            put(2.0, 0.0);
            put(3.0, 0.0);
            put(4.0, 0.0);
            put(5.0, 0.0);
            put(6.0, 0.0);
            put(7.0, 0.0);
            put(8.0, 0.0);
            put(9.0, 0.0);
            put(10.0, 0.0);
            put(11.0, 0.0);
            put(12.0, 0.0);
            put(13.0, 0.0);
            put(14.0, 0.0);
            put(15.0, 0.0);
            put(16.0, 0.0);
            put(17.0, 0.0);
            put(18.0, 0.0);
            put(19.0, 0.0);
        }};

        apacheLogPoints.forEach(apacheLogPoint -> {
            if (apacheLogPoint.getIntermediaryScore() > 0) {
                double key = Math.round(apacheLogPoint.getIntermediaryScore());
                scoreToNumberOfAppearences.replace(key, scoreToNumberOfAppearences.get(key) + 1.0 );
            }
        });

        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Raw Score and its number of appearences").xAxisTitle("Raw Score").yAxisTitle("Number of appearences").theme(Styler.ChartTheme.GGPlot2).build();

        // Series
        chart.addSeries("My category chart",scoreToNumberOfAppearences.keySet().stream().toList(), scoreToNumberOfAppearences.values().stream().toList());
        new SwingWrapper<CategoryChart>(chart).displayChart();

    }

    private static double[] calculateFP_FN_NoOfAttacks(List<ApacheLogPoint> apacheLogPoints)
    {

        double totalNoOfLegitLogs = (int) apacheLogPoints.stream().filter(apacheLogPoint -> apacheLogPoint.LogClass.equals("1")).count();
        double totalNoOfAttackLogs = apacheLogPoints.size() - totalNoOfLegitLogs;
        double falseNegatives = 0;
        double falsePositives = 0;
        for (ApacheLogPoint apacheLogPoint: apacheLogPoints)
        {
            if (Objects.equals(apacheLogPoint.LogClass, "2")) // atac
            {
                if(apacheLogPoint.getPoint()[1]<5)
                {
                    falseNegatives++;
                }
            }
            else
            {
                if (Objects.equals(apacheLogPoint.LogClass, "1")) // acces legitim
                {
                    if(apacheLogPoint.getPoint()[1]>5)
                    {
                        falsePositives++;
                    }
                }
            }
        }

        return new double[]{falseNegatives, falsePositives, totalNoOfAttackLogs};
    }

    private static void printStatistics(double[] fn_fp_noa)
    {
        System.out.println("Number of false negatives (FN): " + fn_fp_noa[0]);
        System.out.println("Number of false positives (FP): " + fn_fp_noa[1]);
        System.out.println("Total number of attack logs (ALL_ATTACKS): "  + fn_fp_noa[2]);
        System.out.println("FN/ALL_ATTACKS * 100  : " + fn_fp_noa[0]/fn_fp_noa[2] * 100 + "% .");
        System.out.println("FP/ALL_ATTACKS * 100  : " + fn_fp_noa[1]/fn_fp_noa[2] * 100 + "% .");

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
}
