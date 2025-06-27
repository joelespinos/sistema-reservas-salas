package utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class InputValidator {

    private static final String NUMERIC_REGEX = "\\d+";
    private static final String ALPHANUMERIC_REGEX = "[a-zA-Z0-9]+";
    private static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    private static final String TIME_REGEX = "\\d{2}:\\d{2}";
    private static final int MAX_HOURS = 23;
    private static final int MAX_MINUTES = 59;

    public static boolean isStringNotNumValid(String str, int maxChars) {
        return !str.isBlank() && str.length() <= maxChars && !str.matches(NUMERIC_REGEX);
    }

    public static boolean isStringValid(String str, int maxChars) {
        return !str.isBlank() && str.length() <= maxChars;
    }

    public static boolean isStringToUpdateNumValid(String str, int maxChars) {
        return str.length() <= maxChars && !str.matches(NUMERIC_REGEX);
    }

    public static boolean isStringToUpdateValid(String str, int maxChars) {
        return str.length() <= maxChars;
    }

    public static boolean isNumberValid(int number, int minValue) {
        return number >= minValue;
    }

    public static boolean isDateValid(String date) {
        return date.matches(DATE_REGEX)
                && !date.isBlank()
                && !date.equals(LocalDate.now().toString())
                && java.time.LocalDate.parse(date).isAfter(LocalDate.now());
    }

    public static boolean isTimeValidAccordingADate(String time, LocalDate reservationDate) {
        boolean isValid;
        if (!time.matches(TIME_REGEX) || time.isBlank()) {
            isValid = false;
        } else {
            LocalTime inputTime;
            try {
                inputTime = LocalTime.parse(time);
            } catch (Exception e) {
                return false;
            }
            if (reservationDate.equals(LocalDate.now())) {
                // Si la reserva es para hoy, la hora debe ser posterior a la actual
                isValid = inputTime.isAfter(LocalTime.now()) && inputTime.isBefore(LocalTime.of(MAX_HOURS, MAX_MINUTES));
            } else {
                // Si la reserva es para una fecha futura, solo valida el rango de hora/minuto
                isValid = inputTime.isBefore(LocalTime.of(MAX_HOURS, MAX_MINUTES));
            }
        }
        return isValid;
    }

    public static boolean isTimeAfterOptional(String time, Optional<LocalTime> optionalTime) {
        if (optionalTime.isPresent()) {
            LocalTime inputTime = LocalTime.parse(time);
            // Debe ser estrictamente posterior, no igual
            return inputTime.isAfter(optionalTime.get());
        }
        return true;
    }
}
