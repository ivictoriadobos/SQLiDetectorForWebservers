package core.clusterer;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import org.apache.commons.math3.ml.distance.DistanceMeasure;

import java.awt.*;

public class ApacheDistanceMetric implements DistanceMeasure {

    @Override
    public double compute(double[] doubles, double[] doubles1) throws DimensionMismatchException {

        if(doubles.length != doubles1.length) {
            throw new DimensionMismatchException(doubles1.length, doubles1.length);
        }

        int distance = 0;
        for (int i = 0; i< doubles.length; i++)
        {
            distance += Math.pow(Math.abs(doubles[i] - doubles1[i]), 2);
        }

        return Math.sqrt(distance);
    }
}
