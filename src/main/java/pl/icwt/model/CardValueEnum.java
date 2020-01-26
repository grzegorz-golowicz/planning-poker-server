package pl.icwt.model;

public enum CardValueEnum {
    VAL_0("0"),
    VAL_05("0.5"),
    VAL_1("1"),
    VAL_2("2"),
    VAL_3("3"),
    VAL_5("5"),
    VAL_8("8"),
    VAL_13("13"),
    VAL_20("20"),
    VAL_40("40"),
    VAL_100("100"),
    INFINITY("INF"),
    QUESTION_MARK("?");

    public final String label;

    CardValueEnum(String label) {
        this.label = label;
    }
}
