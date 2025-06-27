package view;

import controller.ReservationSystemController;
import java.util.Scanner;

public class ReservationSystemMain {

    private static final int EXIT = 0;
    private static final int REPEAT = -1;

    /**
     * Clase principal del sistema de reservas de salas.
     * Gestiona el menú principal y la navegación entre las distintas vistas del sistema.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        ReservationSystemController controller;
        try {
            controller = new ReservationSystemController();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        int option;

        do {
            System.out.print(getMainMenu());
            try {
                option = Integer.parseInt(keyboard.nextLine());
                switch (option) {
                    case 0:
                        System.out.println("\nSaliendo del programa...");
                        break;

                    case 1:
                        RoomManagerView.roomManagement(keyboard, controller);
                        break;

                    case 2:
                        EmployeeManagerView.employeeManagement(keyboard, controller);
                        break;

                    case 3:
                        ReservationManagerView.reservationManagement(keyboard, controller);
                        break;

                    default:
                        System.out.println("Error! Opción incorrecta.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error! Debe ingresar un número entero.");
                option = REPEAT;
            }

        } while (option != EXIT);
    }

    /**
     * Devuelve el menú principal del sistema como String.
     * @return Menú principal.
     */
    private static String getMainMenu() {
        return """
                \n--- SISTEMA DE RESERVAS DE SALAS ---
                1. Gestión de salas
                2. Gestión de empleados
                3. Gestión de reservas
                0. Salir
                Seleccione una opción:\s""";
    }
}
