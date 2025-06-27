package view;

import controller.ReservationSystemController;
import utils.ReadInput;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Scanner;

public class ReservationManagerView {

    private static final int EXIT = 0;
    private static final int REPEAT = -1;

    /**
     * Menú principal de gestión de reservas.
     * @param keyboard Scanner para leer desde consola.
     * @param controller Controlador del sistema de reservas.
     */
    public static void reservationManagement(Scanner keyboard, ReservationSystemController controller) {
        int reservationOption;
        do {
            System.out.print(getReservationManagementMenu());
            try {
                reservationOption = Integer.parseInt(keyboard.nextLine());
                switch (reservationOption) {
                    case 0:
                        System.out.println("\nVolviendo al menú principal...");
                        break;
                    case 1:
                        if (handlerAddReservation(keyboard, controller)) {
                            System.out.println("\nReservación realizada con éxito.");
                        } else {
                            System.out.println("\nNo se ha podido realizar la reservación.");
                        }
                        break;
                    case 2:
                        if (handlerDeleteReservation(keyboard, controller)) {
                            System.out.println("\nReservación cancelada con éxito.");
                        } else {
                            System.out.println("\nNo se ha podido cancelar la reservación.");
                        }
                        break;
                    default:
                        System.out.println("\nError! Opción incorrecta.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nError! Debe ingresar un número entero.");
                reservationOption = REPEAT;
            }
        } while (reservationOption != EXIT);
    }

    /**
     * Maneja la lógica para cancelar una reserva.
     * @param keyboard Scanner para leer desde consola.
     * @param controller Controlador del sistema de reservas.
     * @return true si la reserva se eliminó correctamente, false en caso contrario.
     */
    private static boolean handlerDeleteReservation(Scanner keyboard, ReservationSystemController controller) {
        boolean isDeleted = false;
        System.out.println("\n--- CANCELAR RESERVA ---");
        try {
            System.out.print("Ingrese el ID de la reserva a cancelar: ");
            int reservationId = Integer.parseInt(keyboard.nextLine());

            if (controller.doesReservationExist(reservationId)) {

                System.out.println("\nDetalles de la reserva a cancelar:");
                System.out.println(controller.getReservationById(reservationId).get());

                System.out.print("\n¿Está seguro de que desea cancelar esta reserva? (S/N): ");
                String confirmation = keyboard.nextLine().trim().toUpperCase();

                if (!confirmation.equals("S")) {
                    System.out.println("\nOperación cancelada por el usuario.");
                    isDeleted = false;
                } else {
                    isDeleted = controller.deleteReservationById(reservationId);
                }
            } else {
                System.out.println("\nError! La reserva con ID " + reservationId + " no existe.\n");
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError! Debe ingresar un valor válido.\n");
        } catch (Exception e) {
            System.out.println("\nError al cancelar la reservación: " + e.getMessage() + "\n");
        }
        return isDeleted;
    }

    /**
     * Maneja la lógica para añadir una nueva reserva.
     * @param keyboard Scanner para leer desde consola.
     * @param controller Controlador del sistema de reservas.
     * @return true si la reserva se añadió correctamente, false en caso contrario.
     */
    private static boolean handlerAddReservation(Scanner keyboard, ReservationSystemController controller) {
        boolean isAdded = false;
        Optional<LocalTime> timeToValidateOptional = Optional.empty();

        System.out.println("\n--- AÑADIR NUEVA RESERVA ---");
        try {
            System.out.print("Ingrese el ID de la sala: ");
            int roomId = Integer.parseInt(keyboard.nextLine());

            if (controller.doesRoomExist(roomId)) {
                System.out.print("Ingrese el ID del empleado: ");
                int employeeId = Integer.parseInt(keyboard.nextLine());

                if (controller.doesEmployeeExist(employeeId)) {
                    LocalDate reservationDate = LocalDate.parse(
                            ReadInput.readDate(keyboard, "fecha de reservación"));

                    LocalTime startTime = LocalTime.parse(
                            ReadInput.readTimeAccordingADate(keyboard, "hora de inicio", timeToValidateOptional, reservationDate));

                    timeToValidateOptional = Optional.of(startTime);
                    LocalTime endTime = LocalTime.parse(
                            ReadInput.readTimeAccordingADate(keyboard, "hora de fin", timeToValidateOptional, reservationDate));

                    Optional<Integer> reservationIdOverlap = controller.validateReservationTime(roomId, reservationDate, startTime, endTime);
                    if (reservationIdOverlap.isPresent()) {
                        System.out.println("\nError! La sala ya está reservada en ese horario por:");
                        System.out.println(controller.getReservationById(reservationIdOverlap.get()).get());
                    } else {
                        isAdded = controller.insertNewReservation(roomId, employeeId, reservationDate, startTime, endTime);
                    }
                } else {
                    System.out.println("\nError! El empleado con ID " + employeeId + " no existe.\n");
                }

            } else {
                System.out.println("\nError! La sala con ID " + roomId + " no existe.\n");
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError! Debe ingresar un valor válido.\n");
        } catch (Exception e) {
            System.out.println("\nError al realizar la reservación: " + e.getMessage() + "\n");
        }
        return isAdded;
    }

    /**
     * Devuelve el menú de gestión de reservas como String.
     * @return Menú de gestión de reservas.
     */
    private static String getReservationManagementMenu() {
        return """
                \n--- GESTIÓN DE RESERVAS ---
                1. Reservar sala
                2. Cancelar reservación
                0. Volver al menú principal
                Seleccione una opción:\s""";
    }
}
