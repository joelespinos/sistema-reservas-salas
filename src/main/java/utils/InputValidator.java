package utils;

import controller.ReservationSystemController;

import java.sql.SQLException;
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
    private static final String DNI_REGEX = "[0-9]{8}[A-Za-z]";

    /**
     * Valida que una cadena no sea numérica, no esté en blanco y no supere el máximo de caracteres.
     * @param str Cadena a validar.
     * @param maxChars Número máximo de caracteres permitidos.
     * @return true si la cadena es válida, false en caso contrario.
     */
    public static boolean isStringNotNumValid(String str, int maxChars) {
        return !str.isBlank() && str.length() <= maxChars && !str.matches(NUMERIC_REGEX);
    }

    /**
     * Valida que una cadena no esté en blanco y no supere el máximo de caracteres.
     * @param str Cadena a validar.
     * @param maxChars Número máximo de caracteres permitidos.
     * @return true si la cadena es válida, false en caso contrario.
     */
    public static boolean isStringValid(String str, int maxChars) {
        return !str.isBlank() && str.length() <= maxChars;
    }

    /**
     * Valida que una cadena (para actualización) no supere el máximo de caracteres y no sea numérica.
     * @param str Cadena a validar.
     * @param maxChars Número máximo de caracteres permitidos.
     * @return true si la cadena es válida, false en caso contrario.
     */
    public static boolean isStringToUpdateNumValid(String str, int maxChars) {
        return str.length() <= maxChars && !str.matches(NUMERIC_REGEX);
    }

    /**
     * Valida que una cadena (para actualización) no supere el máximo de caracteres.
     * @param str Cadena a validar.
     * @param maxChars Número máximo de caracteres permitidos.
     * @return true si la cadena es válida, false en caso contrario.
     */
    public static boolean isStringToUpdateValid(String str, int maxChars) {
        return str.length() <= maxChars;
    }

    /**
     * Valida que un número sea mayor o igual que el valor mínimo especificado.
     * @param number Número a validar.
     * @param minValue Valor mínimo permitido.
     * @return true si el número es válido, false en caso contrario.
     */
    public static boolean isNumberValid(int number, int minValue) {
        return number >= minValue;
    }

    /**
     * Valida que una fecha esté en formato correcto, no sea la fecha actual y sea posterior a hoy.
     * @param date Cadena de fecha a validar (formato YYYY-MM-DD).
     * @return true si la fecha es válida, false en caso contrario.
     */
    public static boolean isDateValid(String date) {
        return date.matches(DATE_REGEX)
                && !date.isBlank()
                && !date.equals(LocalDate.now().toString())
                && java.time.LocalDate.parse(date).isAfter(LocalDate.now());
    }

    /**
     * Valida que una hora sea válida según la fecha de reserva.
     * Si la fecha es hoy, la hora debe ser posterior a la actual.
     * Si la fecha es futura, solo valida el rango de hora/minuto.
     * @param time Cadena de hora a validar (formato HH:MM).
     * @param reservationDate Fecha de la reserva.
     * @return true si la hora es válida, false en caso contrario.
     */
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

    /**
     * Valida que una hora sea estrictamente posterior a un Optional<LocalTime> (no puede ser igual).
     * @param time Cadena de hora a validar (formato HH:MM).
     * @param optionalTime Hora base opcional para comparar.
     * @return true si la hora es posterior, false en caso contrario.
     */
    public static boolean isTimeAfterOptional(String time, Optional<LocalTime> optionalTime) {
        if (optionalTime.isPresent()) {
            LocalTime inputTime = LocalTime.parse(time);
            // Debe ser estrictamente posterior, no igual
            return inputTime.isAfter(optionalTime.get());
        }
        return true;
    }

    /**
     * Valida que un DNI sea correcto y no exista ya en el sistema.
     * @param dni DNI a validar.
     * @param controller Controlador para comprobar unicidad.
     * @return true si el DNI es válido y único, false en caso contrario.
     * @throws SQLException si ocurre un error en la base de datos.
     */
    public static boolean isDNIValid(String dni, ReservationSystemController controller) throws SQLException {
        return dni.matches(DNI_REGEX)
                && !dni.isBlank()
                && dni.length() == 9
                && Character.isLetter(dni.charAt(8))
                && Character.isDigit(dni.charAt(0))
                && !controller.doesDNIExist(dni);
    }
}
