package core.clusterer.acceslogtype;

import org.apache.commons.math3.ml.clustering.Clusterable;

public class ApacheLogPoint implements Clusterable {

    public String LogClass = "";

    public ApacheLog apacheLog;
    public double[] Score;
    private double lengthOfPayload;

    private double numberOfSQLKeywordsInPayload;

    private double weightedSumOfSQLKeywordsInPayload;

    private double numberOfSpacesInPayload;

    private double numberOfSpecialCharacters;
    private double weightedSumOfSpecialCharacters;

    public ApacheLogPoint(double aLength,
                          double aNumberOfSQLKeywords,
                          double aWeightedSumOfSQLKeywords,
                          double aNumberOfSpaces,
                          double aNumberOfSpecialCharacters,
                          double aWeightedSumOfSpecialCharacters,
                          String aLogClass,
                          double[] aScore,
                          ApacheLog anApacheLog) {

        lengthOfPayload = aLength;
        numberOfSQLKeywordsInPayload = aNumberOfSQLKeywords;
        weightedSumOfSQLKeywordsInPayload = aWeightedSumOfSQLKeywords;
        numberOfSpacesInPayload = aNumberOfSpaces;
        numberOfSpecialCharacters = aNumberOfSpecialCharacters;
        weightedSumOfSpecialCharacters = aWeightedSumOfSpecialCharacters;
        LogClass = aLogClass;

        Score = new double[2];
        Score[0] = aScore[0];
        Score[1] = aScore[1];

        apacheLog = anApacheLog;
    }

    @Override
    public double[] getPoint() {
//        return new double[]{lengthOfPayload,
//                            numberOfSQLKeywordsInPayload,
//                            weightedSumOfSQLKeywordsInPayload,
//                            numberOfSpacesInPayload,
//                            numberOfSpecialCharactersInPayload};
        return Score;
    }

    public double getIntermediaryScore()
    {
        return weightedSumOfSQLKeywordsInPayload + weightedSumOfSpecialCharacters;
    }
}
