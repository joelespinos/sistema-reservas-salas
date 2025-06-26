package utils;

public class InputValidator {

    public static boolean isStringNotNumValid(String str, int maxChars) {
        return !str.isBlank() && str.length() <= maxChars && !str.matches("\\d+");
    }

    public static boolean isStringValid(String str, int maxChars) {
        return !str.isBlank() && str.length() <= maxChars;
    }

    public static boolean isStringToUpdateNumValid(String str, int maxChars) {
        return str.length() <= maxChars && !str.matches("\\d+");
    }

    public static boolean isStringToUpdateValid(String str, int maxChars) {
        return str.length() <= maxChars;
    }

    public static boolean isNumberValid(int number, int minValue) {
        return number >= minValue;
    }
}
