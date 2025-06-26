package view;

import controller.ReservationSystemController;
import model.pojo.Room;
import utils.ReadInput;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class RoomManagerView {

    private static final int EXIT = 0;
    private static final int REPEAT = -1;
    private static final int MAX_NAME_CHARS = 40;;
    private static final int MIN_CAPACITY = 1;
    private static final int MAX_TEXT_CHARS = 65535;

    public static void roomManagement(Scanner keyboard, ReservationSystemController controller) {
        int roomOption;
        do {
            System.out.print(getRoomManagementMenu());
            try {
                roomOption = Integer.parseInt(keyboard.nextLine());
                switch (roomOption) {
                    case 0:
                        System.out.println("Volviendo al menú principal...");
                        break;

                    case 1:
                        if (handleAddRoom(keyboard, controller))
                            System.out.println("La sala se ha agregado correctamente al sistema");
                        else
                            System.out.println("No se ha podido agregar la sala al sistema");
                        break;

                    case 2:
                        if (handleDeleteRoom(keyboard, controller))
                            System.out.println("La sala se ha eliminado correctamente del sistema");
                        else
                            System.out.println("Operación cancelada, no se ha eliminado la sala");
                        break;

                    case 3:
                        if (handleUpdateRoom(keyboard, controller)) {
                            System.out.println("La sala se ha actualizado correctamente");
                        } else {
                            System.out.println("No se ha podido actualizar la sala en el sistema");
                        }
                        break;

                    case 4:
                        handleSelectAllRooms(controller);
                        break;

                    default:
                        System.out.println("Error! Opción incorrecta.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error! Debe ingresar un número entero.");
                roomOption = REPEAT;
            }
        } while (roomOption != EXIT);
    }

    private static String getRoomManagementMenu() {
        return """
                \n--- GESTIÓN DE SALAS ---
                1. Agregar nueva sala
                2. Eliminar sala
                3. Modificar datos de sala
                4. Ver listado de todas las salas
                0. Volver al menú principal
                Seleccione una opción:\s""";
    }

    private static boolean handleAddRoom(Scanner keyboard, ReservationSystemController controller) {
        boolean isAdded = false;
        System.out.println("\n--- AGREGAR NUEVA SALA ---");
        String roomName = ReadInput.readNonEmptyString(keyboard, "el nombre de la sala", MAX_NAME_CHARS);
        int capacity = ReadInput.readPositiveInt(keyboard, "la capacidad de la sala", MIN_CAPACITY);
        String resources = ReadInput.readNonEmptyString(keyboard, "los recursos disponibles de la sala", MAX_TEXT_CHARS);

        try {
            isAdded = controller.insertNewRoom(roomName, capacity, resources);
        } catch (SQLException e) {
            System.out.println("ERROR SQL, no se ha podido agregar la sala en el sistema");
        }
        return isAdded;
    }

    private static boolean handleDeleteRoom(Scanner keyboard, ReservationSystemController controller) {
        boolean isDeleted = false;
        System.out.println("\n--- BORRAR UNA SALA ---");
        int roomId;

        try {
            System.out.print("Introduzca el ID de la sala a eliminar: ");
            roomId = Integer.parseInt(keyboard.nextLine());

            if (!controller.doesRoomExist(roomId)) {
                System.out.println("ERROR! No existe una sala con el ID " + roomId);
            } else {
                Optional<Room> roomOptional = controller.getRoomById(roomId);
                if (roomOptional.isPresent()) {
                    System.out.println("La información de la sala a eliminar es:");
                    System.out.println(roomOptional.get());

                    System.out.print("¿Está seguro de que desea eliminar esta sala? (S/N): ");
                    String confirmation = keyboard.nextLine().trim().toUpperCase();

                    if (confirmation.equals("S"))
                        isDeleted = controller.deleteRoomById(roomId);
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR SQL, no se ha podido eliminar la sala del sistema");
        }
        return isDeleted;
    }

    private static boolean handleUpdateRoom(Scanner keyboard, ReservationSystemController controller) {
        boolean isUpdated = false;
        int roomId;
        System.out.println("\n--- MODIFICAR DATOS DE SALA ---");
        try {
            System.out.print("Introduzca el ID de la sala a modificar: ");
            roomId = Integer.parseInt(keyboard.nextLine());

            if (!controller.doesRoomExist(roomId)) {
                System.out.println("ERROR! No existe una sala con el ID " + roomId);
            } else {
                Optional<Room> roomOptional = controller.getRoomById(roomId);
                if (roomOptional.isPresent()) {
                    Room roomToUpdate = roomOptional.get();
                    System.out.println("La información de la sala es:");
                    System.out.println(roomToUpdate);

                    System.out.println("Introduzca los nuevos datos de la sala (deje en blanco para no modificar):");
                    String newName = ReadInput.readString(keyboard, roomToUpdate.getName(), "el nuevo nombre de la sala", MAX_NAME_CHARS);
                    int newCapacity = ReadInput.readIntMinValue(keyboard, roomToUpdate.getCapacity(), "la nueva capacidad de la sala", MIN_CAPACITY);
                    String newResources = ReadInput.readString(keyboard, roomToUpdate.getResources(),"los nuevos recursos disponibles de la sala", MAX_TEXT_CHARS);

                    isUpdated = controller.updateInfoRoom(roomId, newName, newCapacity, newResources);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR SQL, no se ha podido actualizar la sala del sistema");
        } catch (NumberFormatException e) {
            System.out.println("Error! Debe ingresar un número entero.");
        }
        return isUpdated;
    }

    private static void handleSelectAllRooms(ReservationSystemController controller) {
        try {
            ArrayList<Room> roomsList = controller.getAllRooms();
            if (roomsList.isEmpty()) {
                System.out.println("No hay salas registradas en el sistema.");
            } else {
                System.out.println("\n--- LISTADO DE SALAS ---");
                for (Room room : roomsList) {
                    System.out.println(room);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR SQL, no se ha podido obtener el listado de salas del sistema");
        }
    }
}
