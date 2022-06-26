package core.constants;

public enum WeightClassEnum {
    CRITICAL(1.1),
    HIGH(0.7),
    MEDIUM(0.4),
    LOW(0.2),
    EXTRA_LOW(0.1);

    private final double value;
    WeightClassEnum(double i) {
        value = i;
    }

    public double getValue() {
        return value;
    }
}
