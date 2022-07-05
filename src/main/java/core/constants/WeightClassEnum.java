package core.constants;

public enum WeightClassEnum {
    CRITICAL(2),
    HIGH(1.1),
    MEDIUM(0.6),
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
