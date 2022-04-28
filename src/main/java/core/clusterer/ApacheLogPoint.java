package core.clusterer;

import org.apache.commons.math3.ml.clustering.Clusterable;

public class ApacheLogPoint implements Clusterable {

    private double lengthOfPayload;

    private double numberOfSQLKeywordsInPayload;

    private double weightedSumOfSQLKeywordsInPayload;

    private double numberOfSpacesInPayload;

    private double numberOfSpecialCharactersInPayload;

    public ApacheLogPoint(double lengthOfPayload, double numberOfSQLKeywordsInPayload, double weightedSumOfSQLKeywordsInPayload, double numberOfSpacesInPayload, double numberOfSpecialCharactersInPayload) {

        this.lengthOfPayload = lengthOfPayload;
        this.numberOfSQLKeywordsInPayload = numberOfSQLKeywordsInPayload;
        this.weightedSumOfSQLKeywordsInPayload = weightedSumOfSQLKeywordsInPayload;
        this.numberOfSpacesInPayload = numberOfSpacesInPayload;
        this.numberOfSpecialCharactersInPayload = numberOfSpecialCharactersInPayload;
    }

    @Override
    public double[] getPoint() {
        return new double[]{lengthOfPayload,
                            numberOfSQLKeywordsInPayload,
                            weightedSumOfSQLKeywordsInPayload,
                            numberOfSpacesInPayload,
                            numberOfSpecialCharactersInPayload};
    }
}
