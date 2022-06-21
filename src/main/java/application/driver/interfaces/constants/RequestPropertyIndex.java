package application.driver.interfaces.constants;

public enum RequestPropertyIndex {

    TIMESTAMP(0),
    IPSRCADDRESS(1),
    REQUEST_LINE(2),
    HEADERS(3);

    private final int idx;
    RequestPropertyIndex(int i) {
        idx = i;
    }

    public int getIdx() {
        return idx;
    }
}
