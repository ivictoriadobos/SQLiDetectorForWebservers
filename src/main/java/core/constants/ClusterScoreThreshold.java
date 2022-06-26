package core.constants;

public enum ClusterScoreThreshold {

    NORMAL_ACCESS_LOWER_THRESHOLD(0.0),
    NORMAL_ACCESS_UPPER_THRESHOLD(4.5),

    POSSIBLE_ATTACK_LOWER_THRESHOLD(4.5),
    POSSIBLE_ATTACK_UPPER_THRESHOLD(11.0),

    ATTACK_LOWER_THRESHOLD(11),
    ATTACK_UPPER_THRESHOLD(Double.MAX_VALUE);


    private final double thresholdValue;
    ClusterScoreThreshold(double aThresholdValue) {
        thresholdValue = aThresholdValue;
    }

    public double getValue() {
        return thresholdValue;
    }
}
