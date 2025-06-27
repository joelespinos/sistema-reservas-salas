package utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Scanner;

public class ReadInput {

    private static final int REPEAT = -1;

    public static int readIntMinValue(Scanner keyboard, int oldValue, String prompt, int minValue) {
        String numberStr;
        int number;
        do {
            try {
                System.out.print("Ingrese " + prompt + ": ");
                numberStr = keyboard.nextLine();
                if (numberStr.isBlank()) {
                    number = oldValue;
                } else {
                    number = Integer.parseInt(numberStr);

                    if (!InputValidator.isNumberValid(number, minValue))
                        System.out.println("ERROR! " + prompt + " debe ser mayor o igual a " + minValue);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error! Debe ingresar un número entero.");
                number = REPEAT;
            }
        } while (!InputValidator.isNumberValid(number, minValue));
        return number;
    }

    public static int readPositiveInt(Scanner keyboard, String prompt, int minValue) {
        String numberStr;
        int number;
        do {
            try {
                do {
                    System.out.print("Ingrese " + prompt + ": ");
                    numberStr = keyboard.nextLine();
                    if (numberStr.isBlank())
                        System.out.println("ERROR! " + prompt + " no puede estar en blanco o vacío");
                } while (numberStr.isBlank());

                number = Integer.parseInt(numberStr);

                if (!InputValidator.isNumberValid(number, minValue))
                    System.out.println("ERROR! " + prompt + " debe ser mayor o igual a " + minValue);

            } catch (NumberFormatException e) {
                System.out.println("Error! Debe ingresar un número entero.");
                number = REPEAT;
            }
        } while (!InputValidator.isNumberValid(number, minValue));
        return number;
    }

    public static String readString(Scanner keyboard, String oldValue, String prompt, int maxChars) {
        String newValue;
        do {
            System.out.print("Ingrese " + prompt + ": ");
            newValue = keyboard.nextLine();

            if (newValue.isBlank())
                newValue = oldValue;

            else if (newValue.length() > maxChars)
                System.out.println("ERROR, " + prompt + " es demasiado largo (max " + maxChars + " caracteres)");

            else if (newValue.matches("\\d+"))
                System.out.println("ERROR, " + prompt + " no puede ser solo numerico");
        } while (!InputValidator.isStringToUpdateNumValid(newValue, maxChars));

        return newValue;
    }

    public static String readStringNum(Scanner keyboard, String oldValue, String prompt, int maxChars) {
        String newValue;
        do {
            System.out.print("Ingrese " + prompt + ": ");
            newValue = keyboard.nextLine();

            if (newValue.isBlank())
                newValue = oldValue;

            else if (newValue.length() > maxChars)
                System.out.println("ERROR, " + prompt + " es demasiado largo (max " + maxChars + " caracteres)");

        } while (!InputValidator.isStringToUpdateValid(newValue, maxChars));

        return newValue;
    }

    public static String readNonEmptyString(Scanner keyboard, String prompt, int maxChars) {
        String str;
        do {
            System.out.print("Ingrese " + prompt + ": ");
            str = keyboard.nextLine();

            if (str.isBlank())
                System.out.println("ERROR! " + prompt + " no puede estar en blanco o vacío");

            else if (str.length() > maxChars)
                System.out.println("ERROR, " + prompt + " es demasiado largo (max " + maxChars + " caracteres)");

            else if (str.matches("\\d+"))
                System.out.println("ERROR, " + prompt + " no puede ser solo numerico");
        } while (!InputValidator.isStringNotNumValid(str, maxChars));

        return str;
    }

    public static String readNonEmptyStringNum(Scanner keyboard, String prompt, int maxChars) {
        String str;
        do {
            System.out.print("Ingrese " + prompt + ": ");
            str = keyboard.nextLine();

            if (str.isBlank())
                System.out.println("ERROR! " + prompt + " no puede estar en blanco o vacío");

            else if (str.length() > maxChars)
                System.out.println("ERROR, " + prompt + " es demasiado largo (max " + maxChars + " caracteres)");

        } while (!InputValidator.isStringValid(str, maxChars));

        return str;
    }

    public static String readDate(Scanner keyboard, String prompt) {
        String date;
        do {
            System.out.print("Ingrese " + prompt + " (YYYY-MM-DD): ");
            date = keyboard.nextLine();

            if (!InputValidator.isDateValid(date)) {
                System.out.println("ERROR! Fecha no válida.");
            }
        } while (!InputValidator.isDateValid(date));
        return date;
    }

    public static String readTimeAccordingADate(Scanner keyboard, String prompt,
                                                Optional<LocalTime> optionalTime, LocalDate reservationDate) {
        String time;
        do {
            System.out.print("Ingrese " + prompt + " (HH:MM): ");
            time = keyboard.nextLine();

            if (!InputValidator.isTimeValidAccordingADate(time, reservationDate)) {
                System.out.println("ERROR! Hora no válida.");
            }
            else if (!InputValidator.isTimeAfterOptional(time, optionalTime)) {
                System.out.println("ERROR! La hora debe ser posterior a " + optionalTime.get());
            }
        } while (!InputValidator.isTimeValidAccordingADate(time, reservationDate) || !InputValidator.isTimeAfterOptional(time, optionalTime));
        return time;
    }
}
