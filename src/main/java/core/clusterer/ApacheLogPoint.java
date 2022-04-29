package core.clusterer;

import org.apache.commons.logging.Log;
import org.apache.commons.math3.ml.clustering.Clusterable;

public class ApacheLogPoint implements Clusterable {

    public String LogClass = "";

    public double[] Score;
    private double lengthOfPayload;

    private double numberOfSQLKeywordsInPayload;

    private double weightedSumOfSQLKeywordsInPayload;

    private double numberOfSpacesInPayload;

    private double numberOfSpecialCharactersInPayload;

    public ApacheLogPoint(double aLength,
                          double aNumberOfSQLKeywords,
                          double aWeightedSumOfSQLKeywords,
                          double aNumberOfSpaces,
                          double aNumberOfSpecialCharacters,
                          String aLogClass,
                          double[] aScore) {

        lengthOfPayload = aLength;
        numberOfSQLKeywordsInPayload = aNumberOfSQLKeywords;
        weightedSumOfSQLKeywordsInPayload = aWeightedSumOfSQLKeywords;
        numberOfSpacesInPayload = aNumberOfSpaces;
        numberOfSpecialCharactersInPayload = aNumberOfSpecialCharacters;
        LogClass = aLogClass;

        Score = new double[2];
        Score[0] = aScore[0];
        Score[1] = aScore[1];
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
}
