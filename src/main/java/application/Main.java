package application;

import application.driver.implementations.Driver;
import application.driver.exceptions.ApplicationException;
import application.driver.exceptions.ExceptionReasonEnum;
import application.driver.implementations.services.ConsoleInputServiceImpl;
import application.driver.implementations.services.ConsoleOutputServiceImpl;
import application.driver.interfaces.IDriverClass;
import application.io.logprovider.ApacheLogProvider;
import core.clusterer.acceslogtype.ApacheDistanceMetric;
import core.clusterer.acceslogtype.ApacheLogPoint;
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
/*        List<ApacheLogPoint> apacheLogPoints = loadLogs();

        printStatistics(calculateFP_FN_NoOfAttacks(apacheLogPoints));

        List<Cluster<ApacheLogPoint>> clusters = clusterLogPoints(2, 10, apacheLogPoints);

        XYChart branchedFunctionClusteredPointsRepresentation = new XYChart(1200, 1600);

        // adnotare manuala vs adnotare automata
        double totalNoOfLegitLogs = (int) apacheLogPoints.stream().filter(apacheLogPoint -> apacheLogPoint.LogClass.equals("1")).count();
        double totalNoOfAttackLogs = apacheLogPoints.size() - totalNoOfLegitLogs;
        CategoryChart mysecond = new CategoryChartBuilder().width(800).height(600).title("Analiza calitatii gruparii automate comparativ cu eticheta reala").xAxisTitle("Tipar de acces").yAxisTitle("Numar").theme(Styler.ChartTheme.GGPlot2).build();
        mysecond.addSeries("Adnotare manuala", new double[]{0, 1}, new double[]{totalNoOfLegitLogs, totalNoOfAttackLogs});
        double[] noOfLegistvsNoOfAttackWhenClustered = {0,0};

        int i = 0;
        int numberOfClusteredPoints = 0;
        for( Cluster<ApacheLogPoint> cluster: clusters)
        {

            List<ApacheLogPoint> points = cluster.getPoints();
            numberOfClusteredPoints += points.size();

            if( i == 0 || i==1)
            {
                List<ApacheLogPoint> prettyHighScoreLegitLogs = cluster.getPoints().stream().filter(apacheLogPoint -> (apacheLogPoint.Score[0] >= 103)).toList();

                noOfLegistvsNoOfAttackWhenClustered[0] += cluster.getPoints().size();
            }
            else
            {
                List<ApacheLogPoint> lowScoreAttackLogs = cluster.getPoints().stream().filter(apacheLogPoint -> (apacheLogPoint.Score[0] <= 110)).toList();
                noOfLegistvsNoOfAttackWhenClustered[1] +=cluster.getPoints().size();
            }

            if (points.get(0).Score[1] != 0) {
                i++;
                System.out.println("Skipping " + points.size() + " points, no worries");
                continue;
            }

            List<Double> rawScore = new ArrayList<>();
            List<Double> manipulatedScore = new ArrayList<>();

            points.forEach(apacheLogPoint -> {
                rawScore.add(apacheLogPoint.getIntermediaryScore()+100);
                manipulatedScore.add(apacheLogPoint.Score[0]);
            });


            branchedFunctionClusteredPointsRepresentation.addSeries(indexToClusterNameMap.get(i), rawScore, manipulatedScore);
            i++;
        }
        mysecond.addSeries("Adnotare automata", new double[]{0,1}, noOfLegistvsNoOfAttackWhenClustered);
        new SwingWrapper<CategoryChart>(mysecond).displayChart();

        branchedFunctionClusteredPointsRepresentation.setXAxisTitle("Suma ponderata numarului dx`e cuvinte SQL/ caractere speciale");
        branchedFunctionClusteredPointsRepresentation.setYAxisTitle("Valoarea sumei dupa aplicarea functiei de normalizare");
        new SwingWrapper<XYChart>(branchedFunctionClusteredPointsRepresentation).displayChart();


        System.out.println("Number of unclustered and lost points: " + (apacheLogPoints.size() - numberOfClusteredPoints));
        System.out.println("Clusters found: " + clusters.size());
 */

        if (args.length == 1 && (Objects.equals(args[0], "--console") || Objects.equals(args[0], "--server"))) {
            // TODO : switch DI according to command line argument
            IDriverClass applicationDriver = new Driver(new ConsoleInputServiceImpl(), new ConsoleOutputServiceImpl());

            applicationDriver.start();

        } else throw new ApplicationException(ExceptionReasonEnum.INVALID_ARGUMENTS);
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

    private static List<Cluster<ApacheLogPoint>> clusterLogs(double eps, double minPts, List<ApacheLogPoint> apacheLogPoints)
    {
        return null;
    }
    private static void buildGraph()
    {
        SimpleGraph simpleGraphLegitAccess = new SimpleGraph();
        simpleGraphLegitAccess.display();

        simpleGraphLegitAccess.setGridSpreadX(20);
        simpleGraphLegitAccess.setGridSpreadY(20);
    }

    private static List<ApacheLogPoint> loadLogs()
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

    private static void noOfSQLKeywordsToAssociatedWeightedSumGraph(List<ApacheLogPoint> apacheLogPoints)
    {
        List<Double> noOfSQLKeywords = new ArrayList<>();
        List<Double> weightedSumOfSQLKeywords = new ArrayList<>();
        apacheLogPoints.forEach(apacheLogPoint -> {
            noOfSQLKeywords.add((double) apacheLogPoint.apacheLog.getNumberOfSQLKeywords());
            weightedSumOfSQLKeywords.add((double) apacheLogPoint.apacheLog.getWeightedSumOfSQLKeywords());
        });
        buildXYChart(XYSeries.XYSeriesRenderStyle.Scatter, noOfSQLKeywords, weightedSumOfSQLKeywords, "noOfSQLKeywordsToWeightedSumPerRequest");
    }

    private static void noOfSpecialCharactersToAssociatedWeightedSumGraph(List<ApacheLogPoint> apacheLogPoints)
    {

        List<Double> noOfSpecialCharacters = new ArrayList<>();
        List<Double> weightedSumOfSpecialCharacters = new ArrayList<>();
        apacheLogPoints.forEach(apacheLogPoint -> {
            noOfSpecialCharacters.add((double) apacheLogPoint.apacheLog.getNumberOfSpecialCharacters());
            weightedSumOfSpecialCharacters.add((double) apacheLogPoint.apacheLog.getWeightedSumOfSpecialCharacters());
        });

        buildXYChart(XYSeries.XYSeriesRenderStyle.Scatter, noOfSpecialCharacters, weightedSumOfSpecialCharacters, "noOfSpecialCharactersToWeightedSumPerRequest");
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
                if(apacheLogPoint.Score[0]<105)
                {
                    falseNegatives++;
                }
            }
            else
            {
                if (Objects.equals(apacheLogPoint.LogClass, "1")) // acces legitim
                {
                    if(apacheLogPoint.Score[0]>105)
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

    private static List<Cluster<ApacheLogPoint>> clusterLogPoints(double eps, int minPts, List<ApacheLogPoint> apacheLogPoints)
    {
        ApacheDistanceMetric distanceMetric = new ApacheDistanceMetric();

        DBSCANClusterer<ApacheLogPoint> clusterer = new DBSCANClusterer<>(eps, minPts, distanceMetric);

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
        put(2, "Cereri care contin un posibil atac");
        put(3, "Cereri de atac");
    }};
}
