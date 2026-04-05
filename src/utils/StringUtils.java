package utils;

public class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean notEquals(String value1, String value2) {
        return !value1.equals(value2);
    }

}
