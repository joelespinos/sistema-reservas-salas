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

    public static void reservationManagement(Scanner keyboard, ReservationSystemController controller) {
        int reservationOption;
        do {
            System.out.print(getReservationManagementMenu());
            try {
                reservationOption = Integer.parseInt(keyboard.nextLine());
                switch (reservationOption) {
                    case 0:
                        System.out.println("Volviendo al menú principal...");
                        break;
                    case 1:
                        if (handlerAddReservation(keyboard, controller)) {
                            System.out.println("Reservación realizada con éxito.");
                        } else {
                            System.out.println("No se ha podido realizar la reservación.");
                        }
                        break;
                    case 2:
                        if (handlerDeleteReservation(keyboard, controller)) {
                            System.out.println("Reservación cancelada con éxito.");
                        } else {
                            System.out.println("No se ha podido cancelar la reservación.");
                        }
                        break;
                    default:
                        System.out.println("Error! Opción incorrecta.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error! Debe ingresar un número entero.");
                reservationOption = REPEAT;
            }
        } while (reservationOption != EXIT);
    }

    private static boolean handlerDeleteReservation(Scanner keyboard, ReservationSystemController controller) {
        boolean isDeleted = false;
        System.out.println("\n--- CANCELAR RESERVA ---");
        try {
            System.out.print("Ingrese el ID de la reserva a cancelar: ");
            int reservationId = Integer.parseInt(keyboard.nextLine());

            if (controller.doesReservationExist(reservationId)) {
                isDeleted = controller.deleteReservationById(reservationId);
            } else {
                System.out.println("Error! La reserva con ID " + reservationId + " no existe.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error! Debe ingresar un valor válido.");
        } catch (Exception e) {
            System.out.println("Error al cancelar la reservación: " + e.getMessage());
        }
        return isDeleted;
    }

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
                        System.out.println("Error! La sala ya está reservada en ese horario por:");
                        System.out.println(controller.getReservationById(reservationIdOverlap.get()));
                    } else {
                        isAdded = controller.insertNewReservation(roomId, employeeId, reservationDate, startTime, endTime);
                    }
                } else {
                    System.out.println("Error! El empleado con ID " + employeeId + " no existe.");
                }

            } else {
                System.out.println("Error! La sala con ID " + roomId + " no existe.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error! Debe ingresar un valor válido.");
        } catch (Exception e) {
            System.out.println("Error al realizar la reservación: " + e.getMessage());
        }
        return isAdded;
    }

    private static String getReservationManagementMenu() {
        return """
                \n--- GESTIÓN DE RESERVAS ---
                1. Reservar sala
                2. Cancelar reservación
                0. Volver al menú principal
                Seleccione una opción:\s""";
    }
}
