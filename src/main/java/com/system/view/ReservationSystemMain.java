package com.system.view;

import com.system.controller.ReservationSystemController;
import com.system.model.pojo.Employee;
import com.system.model.pojo.Room;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReservationSystemMain {

    private static final int EXIT = 0;
    private static final int REPEAT = -1;
    private static final int MAX_NAME_CHARS = 40;
    private static final int MAX_DNI_CHARS = 9;
    private static final int MAX_PHONE_NUMBER_CHARS = 9;
    private static final int MIN_CAPACITY = 1;
    private static final int MAX_TEXT_CHARS = 65535;

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
                        System.out.println("Saliendo del programa...");
                        break;

                    case 1:
                        roomManagement(keyboard, controller);
                        break;

                    case 2:
                        employeeManagement(keyboard, controller);
                        break;

                    case 3:
                        reservationManagement(keyboard, controller);
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

    private static void reservationManagement(Scanner keyboard, ReservationSystemController controller) {
    }

    private static void employeeManagement(Scanner keyboard, ReservationSystemController controller) {
        int employeeOption;
        do {
            System.out.print(getEmployeeManagementMenu());
            try {
                employeeOption = Integer.parseInt(keyboard.nextLine());
                switch (employeeOption) {
                    case 0:
                        System.out.println("Volviendo al menú principal...");
                        break;
                    case 1:
                        if (handleAddEmployee(keyboard, controller))
                            System.out.println("El empleado se ha agregado correctamente al sistema");
                        else
                            System.out.println("No se ha podido agregar el empleado al sistema");
                        break;
                    case 2:
                        if (handleDeleteEmployee(keyboard, controller))
                            System.out.println("El empleado se ha eliminado correctamente del sistema");
                        else
                            System.out.println("Operación cancelada, no se ha eliminado el empleado");
                        break;
                    case 3:
                        if (handleUpdateEmployee(keyboard, controller))
                            System.out.println("El empleado se ha actualizado correctamente");
                        else
                            System.out.println("No se ha podido actualizar el empleado en el sistema");
                        break;
                    case 4:
                        handleSelectAllEmployees(controller);
                        break;
                    default:
                        System.out.println("Error! Opción incorrecta.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error! Debe ingresar un número entero.");
                employeeOption = REPEAT;
            }
        } while (employeeOption != EXIT);
    }

    private static void roomManagement(Scanner keyboard, ReservationSystemController controller) {
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

    private static boolean handleUpdateRoom(Scanner keyboard, ReservationSystemController controller) {
        boolean isUpdated = false;
        int roomId;
        System.out.println("\n--- MODIFICAR DATOS DE SALA ---");
        try {
            do {
                System.out.print("Introduzca el ID de la sala a modificar: ");
                roomId = Integer.parseInt(keyboard.nextLine());

                if (!doesRoomExistById(roomId, controller))
                    System.out.println("ERROR! No existe una sala con el ID " + roomId);

            } while (!doesRoomExistById(roomId, controller));

            Room roomToUpdate = getRoomByHisId(roomId, controller);
            System.out.println("La información de la sala es:");
            System.out.println(roomToUpdate);

            String newName = readString(keyboard, roomToUpdate.getName(), "el nuevo nombre de la sala", MAX_NAME_CHARS);
            int newCapacity = readInt(keyboard, roomToUpdate.getCapacity(), "la nueva capacidad de la sala");
            String newResources = readString(keyboard, roomToUpdate.getResources(),"los nuevos recursos disponibles de la sala", MAX_TEXT_CHARS);

            isUpdated = controller.updateInfoRoom(roomId, newName, newCapacity, newResources);

        } catch (SQLException e) {
            System.out.println("ERROR SQL, no se ha podido actualizar la sala del sistema");
        }
        catch (NumberFormatException e) {
            System.out.println("Error! Debe ingresar un número entero.");
        }
        return isUpdated;
    }

    private static int readInt(Scanner keyboard, int oldValue, String prompt) {
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

                    if (number < MIN_CAPACITY)
                        System.out.println("ERROR! " + prompt + " debe ser mayor o igual a " + MIN_CAPACITY);

                }

            } catch (NumberFormatException e) {
                System.out.println("Error! Debe ingresar un número entero.");
                number = REPEAT;
            }
        } while (!isNumberValid(number));
        return number;
    }

    private static String readString(Scanner keyboard, String oldValue, String prompt, int maxChars) {
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
        } while (!isStringToUpdateValid(newValue, maxChars));

        return newValue;
    }

    private static boolean handleDeleteRoom(Scanner keyboard, ReservationSystemController controller) {
        boolean isDeleted = false;
        System.out.println("\n--- BORRAR UNA SALA ---");
        int roomId;

        try {
            do {
                System.out.print("Introduzca el ID de la sala a eliminar: ");
                roomId = Integer.parseInt(keyboard.nextLine());

                if (!doesRoomExistById(roomId, controller))
                    System.out.println("ERROR! No existe una sala con el ID " + roomId);

            } while (!doesRoomExistById(roomId, controller));

            System.out.println("La información de la sala a eliminar es:");
            System.out.println(getRoomByHisId(roomId, controller));

            System.out.print("¿Está seguro de que desea eliminar esta sala? (S/N): ");
            String confirmation = keyboard.nextLine().trim().toUpperCase();

            if (confirmation.equals("S"))
                isDeleted = controller.deleteRoomById(roomId);

        } catch (SQLException e) {
            System.out.println("ERROR SQL, no se ha podido eliminar la sala del sistema");
        }
        return isDeleted;
    }

    private static Room getRoomByHisId(int roomId, ReservationSystemController controller) throws SQLException {
        return controller.getRoomById(roomId).orElse(null);
    }

    private static boolean doesRoomExistById(int roomId, ReservationSystemController controller) throws SQLException {
        return controller.getRoomById(roomId).isPresent();
    }

    private static boolean handleAddRoom(Scanner keyboard, ReservationSystemController controller) {
        boolean isAdded = false;
        System.out.println("\n--- AGREGAR NUEVA SALA ---");
        String roomName = readNonEmptyString(keyboard, "el nombre de la sala", MAX_NAME_CHARS);
        int capacity = readPositiveInt(keyboard, "la capacidad de la sala");
        String resources = readNonEmptyString(keyboard, "los recursos disponibles de la sala", MAX_TEXT_CHARS);

        try {
            isAdded = controller.insertNewRoom(roomName, capacity, resources);
        } catch (SQLException e) {
            System.out.println("ERROR SQL, no se ha podido agregar la sala en el sistema");
        }
        return isAdded;
    }

    private static String readNonEmptyString(Scanner keyboard, String prompt, int maxChars) {
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
        } while (!isStringValid(str, maxChars));

        return str;
    }

    private static boolean isStringToUpdateValid(String str, int maxChars) {
        return str.length() <= maxChars && !str.matches("\\d+");
    }

    private static boolean isStringValid(String str, int maxChars) {
        return !str.isBlank() && str.length() <= maxChars && !str.matches("\\d+");
    }

    private static int readPositiveInt(Scanner keyboard, String prompt) {
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

                if (number < MIN_CAPACITY)
                    System.out.println("ERROR! " + prompt + " debe ser mayor o igual a " + MIN_CAPACITY);

            } catch (NumberFormatException e) {
                System.out.println("Error! Debe ingresar un número entero.");
                number = REPEAT;
            }
        } while (!isNumberValid(number));
        return number;
    }

    private static boolean isNumberValid(int number) {
        return number >= MIN_CAPACITY;
    }

    private static String getEmployeeManagementMenu() {
        return """
                \n--- GESTIÓN DE EMPLEADOS ---
                1. Agregar nuevo empleado
                2. Eliminar empleado
                3. Modificar datos de empleado
                4. Ver listado de todos los empleados
                0. Volver al menú principal
                Seleccione una opción:\s""";
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

    private static String getMainMenu() {
        return """
                \n--- SISTEMA DE RESERVAS DE SALAS ---
                1. Gestión de salas
                2. Gestión de empleados
                3. Gestión de reservas
                0. Salir
                Seleccione una opción:\s""";
    }

    private static boolean handleAddEmployee(Scanner keyboard, ReservationSystemController controller) {
        boolean isAdded = false;
        System.out.println("\n--- AGREGAR NUEVO EMPLEADO ---");
        String dni = readNonEmptyString(keyboard, "el DNI del empleado", MAX_DNI_CHARS);
        String firstName = readNonEmptyString(keyboard, "el nombre del empleado", MAX_NAME_CHARS);
        String lastName1 = readNonEmptyString(keyboard, "el primer apellido del empleado", MAX_NAME_CHARS);
        String lastName2 = readNonEmptyString(keyboard, "el segundo apellido del empleado", MAX_NAME_CHARS);
        String phoneNumber = readNonEmptyString(keyboard, "el número de teléfono del empleado", MAX_PHONE_NUMBER_CHARS);
        String email = readNonEmptyString(keyboard, "el email del empleado", MAX_NAME_CHARS);
        String department = readNonEmptyString(keyboard, "el departamento del empleado", MAX_NAME_CHARS);

        try {
            isAdded = controller.insertNewEmployee(dni, firstName, lastName1, lastName2, phoneNumber, email, department);
        } catch (SQLException e) {
            System.out.println("ERROR SQL, no se ha podido agregar el empleado en el sistema");
        }
        return isAdded;
    }

    private static boolean handleDeleteEmployee(Scanner keyboard, ReservationSystemController controller) {
        boolean isDeleted = false;
        System.out.println("\n--- BORRAR UN EMPLEADO ---");
        int employeeId;

        try {
            do {
                System.out.print("Introduzca el ID del empleado a eliminar: ");
                employeeId = Integer.parseInt(keyboard.nextLine());

                if (!doesEmployeeExistById(employeeId, controller))
                    System.out.println("ERROR! No existe un empleado con el ID " + employeeId);

            } while (!doesEmployeeExistById(employeeId, controller));

            System.out.println("La información del empleado a eliminar es:");
            System.out.println(getEmployeeByHisId(employeeId, controller));

            System.out.print("¿Está seguro de que desea eliminar este empleado? (S/N): ");
            String confirmation = keyboard.nextLine().trim().toUpperCase();

            if (confirmation.equals("S"))
                isDeleted = controller.deleteEmployeeById(employeeId);

        } catch (SQLException e) {
            System.out.println("ERROR SQL, no se ha podido eliminar el empleado del sistema");
        }
        return isDeleted;
    }

    private static boolean handleUpdateEmployee(Scanner keyboard, ReservationSystemController controller) {
        boolean isUpdated = false;
        int employeeId;
        System.out.println("\n--- MODIFICAR DATOS DE EMPLEADO ---");
        try {
            do {
                System.out.print("Introduzca el ID del empleado a modificar: ");
                employeeId = Integer.parseInt(keyboard.nextLine());

                if (!doesEmployeeExistById(employeeId, controller))
                    System.out.println("ERROR! No existe un empleado con el ID " + employeeId);

            } while (!doesEmployeeExistById(employeeId, controller));

            Employee employeeToUpdate = getEmployeeByHisId(employeeId, controller);
            System.out.println("La información del empleado es:");
            System.out.println(employeeToUpdate);

            String newDni = readString(keyboard, employeeToUpdate.getDni(), "el nuevo DNI del empleado", MAX_DNI_CHARS);
            String newFirstName = readString(keyboard, employeeToUpdate.getFirstName(), "el nuevo nombre del empleado", MAX_NAME_CHARS);
            String newLastName1 = readString(keyboard, employeeToUpdate.getLastName1(), "el nuevo primer apellido del empleado", MAX_NAME_CHARS);
            String newLastName2 = readString(keyboard, employeeToUpdate.getLastName2(), "el nuevo segundo apellido del empleado", MAX_NAME_CHARS);
            String newPhoneNumber = readString(keyboard, employeeToUpdate.getPhoneNumber(), "el nuevo número de teléfono del empleado", MAX_PHONE_NUMBER_CHARS);
            String newEmail = readString(keyboard, employeeToUpdate.getEmail(), "el nuevo email del empleado", MAX_NAME_CHARS);
            String newDepartment = readString(keyboard, employeeToUpdate.getDepartment(), "el nuevo departamento del empleado", MAX_NAME_CHARS);

            isUpdated = controller.updateInfoEmployee(employeeId, newDni, newFirstName, newLastName1, newLastName2, newPhoneNumber, newEmail, newDepartment);

        } catch (SQLException e) {
            System.out.println("ERROR SQL, no se ha podido actualizar el empleado del sistema");
        } catch (NumberFormatException e) {
            System.out.println("Error! Debe ingresar un número entero.");
        }
        return isUpdated;
    }

    private static void handleSelectAllEmployees(ReservationSystemController controller) {
        try {
            ArrayList<Employee> employeesList = controller.getAllEmployees();
            if (employeesList.isEmpty()) {
                System.out.println("No hay empleados registrados en el sistema.");
            } else {
                System.out.println("\n--- LISTADO DE EMPLEADOS ---");
                for (Employee employee : employeesList) {
                    System.out.println(employee);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR SQL, no se ha podido obtener el listado de empleados del sistema");
        }
    }

    private static boolean doesEmployeeExistById(int employeeId, ReservationSystemController controller) throws SQLException {
        return controller.getEmployeeById(employeeId) != null;
    }

    private static com.system.model.pojo.Employee getEmployeeByHisId(int employeeId, ReservationSystemController controller) throws SQLException {
        return controller.getEmployeeById(employeeId);
    }
}
