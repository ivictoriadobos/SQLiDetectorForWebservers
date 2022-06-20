package core.constants;

public enum AnaysisResultEnum {
    SAFE("The analysed log doesn't represent an SQLi attack"),
    NOT_SAFE("The analysed log represents an SQLi attack, rejecting it."),
    INCONCLUSIVE("The nature of the analyzed log could not be determined whether or not it is an SQLi attack.");

    private final String description;
    AnaysisResultEnum(String aDescription) {
        description = aDescription;
    }

    public String getDescription() {
        return description;
    }
}
