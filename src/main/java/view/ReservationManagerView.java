package view;

import controller.ReservationSystemController;
import utils.ReadInput;

import java.time.LocalDate;
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
                            System.out.println("La reservación se ha realizado correctamente.");
                        } else {
                            System.out.println("No se ha podido realizar la reservación.");
                        }
                        break;
                    case 2:
                        System.out.println("Cancelar reservación (funcionalidad de prueba)");
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

    private static boolean handlerAddReservation(Scanner keyboard, ReservationSystemController controller) {
        boolean isAdded = false;
        try {
            System.out.print("Ingrese el ID de la sala: ");
            int roomId = Integer.parseInt(keyboard.nextLine());

            if (controller.doesRoomExist(roomId)) {
                System.out.print("Ingrese el ID del empleado: ");
                int employeeId = Integer.parseInt(keyboard.nextLine());

                if (controller.doesEmployeeExist(employeeId)) {
                    System.out.print("Ingrese la fecha (YYYY-MM-DD): ");
                    LocalDate startDate;

                } else {
                    System.out.println("Error! El empleado con ID " + employeeId + " no existe.");
                    return false;
                }

            } else {
                System.out.println("Error! La sala con ID " + roomId + " no existe.");
                isAdded = false;
            }

        } catch (NumberFormatException e) {
            System.out.println("Error! Debe ingresar un número entero válido.");
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
