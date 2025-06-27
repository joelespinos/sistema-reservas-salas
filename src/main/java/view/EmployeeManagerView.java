package view;

import controller.ReservationSystemController;
import model.pojo.Employee;
import utils.ReadInput;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class EmployeeManagerView {

    private static final int EXIT = 0;
    private static final int REPEAT = -1;
    private static final int MAX_NAME_CHARS = 40;
    private static final int MAX_DNI_CHARS = 9;
    private static final int MAX_PHONE_NUMBER_CHARS = 9;

    /**
     * Menú principal de gestión de empleados.
     * @param keyboard Scanner para leer desde consola.
     * @param controller Controlador del sistema de reservas.
     */
    public static void employeeManagement(Scanner keyboard, ReservationSystemController controller) {
        int employeeOption;
        do {
            System.out.print(getEmployeeManagementMenu());
            try {
                employeeOption = Integer.parseInt(keyboard.nextLine());
                switch (employeeOption) {
                    case 0:
                        System.out.println("\nVolviendo al menú principal...");
                        break;
                    case 1:
                        if (handleAddEmployee(keyboard, controller))
                            System.out.println("\nEl empleado se ha agregado correctamente al sistema");
                        else
                            System.out.println("\nNo se ha podido agregar el empleado al sistema");
                        break;
                    case 2:
                        if (handleDeleteEmployee(keyboard, controller))
                            System.out.println("\nEl empleado se ha eliminado correctamente del sistema");
                        else
                            System.out.println("\nOperación cancelada, no se ha eliminado el empleado");
                        break;
                    case 3:
                        if (handleUpdateEmployee(keyboard, controller))
                            System.out.println("\nEl empleado se ha actualizado correctamente");
                        else
                            System.out.println("\nNo se ha podido actualizar el empleado en el sistema");
                        break;
                    case 4:
                        handleSelectAllEmployees(controller);
                        break;
                    default:
                        System.out.println("\nError! Opción incorrecta.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nError! Debe ingresar un número entero.");
                employeeOption = REPEAT;
            }
        } while (employeeOption != EXIT);
    }

    /**
     * Maneja la lógica para agregar un nuevo empleado.
     * @param keyboard Scanner para leer desde consola.
     * @param controller Controlador del sistema de reservas.
     * @return true si el empleado se agregó correctamente, false en caso contrario.
     */
    private static boolean handleAddEmployee(Scanner keyboard, ReservationSystemController controller) {
        boolean isAdded = false;
        System.out.println("\n--- AGREGAR NUEVO EMPLEADO ---");
        String dni = ReadInput.readDNI(keyboard, "el DNI del empleado", controller);
        String firstName = ReadInput.readNonEmptyString(keyboard, "el nombre del empleado", MAX_NAME_CHARS);
        String lastName1 = ReadInput.readNonEmptyString(keyboard, "el primer apellido del empleado", MAX_NAME_CHARS);
        String lastName2 = ReadInput.readNonEmptyString(keyboard, "el segundo apellido del empleado", MAX_NAME_CHARS);
        String phoneNumber = ReadInput.readNonEmptyStringNum(keyboard, "el número de teléfono del empleado", MAX_PHONE_NUMBER_CHARS);
        String email = ReadInput.readNonEmptyString(keyboard, "el email del empleado", MAX_NAME_CHARS);
        String department = ReadInput.readNonEmptyString(keyboard, "el departamento del empleado", MAX_NAME_CHARS);

        try {
            isAdded = controller.insertNewEmployee(dni, firstName, lastName1, lastName2, phoneNumber, email, department);
        } catch (SQLException e) {
            System.out.println("\nERROR SQL, no se ha podido agregar el empleado en el sistema\n");
        }
        return isAdded;
    }

    /**
     * Maneja la lógica para eliminar un empleado.
     * @param keyboard Scanner para leer desde consola.
     * @param controller Controlador del sistema de reservas.
     * @return true si el empleado se eliminó correctamente, false en caso contrario.
     */
    private static boolean handleDeleteEmployee(Scanner keyboard, ReservationSystemController controller) {
        boolean isDeleted = false;
        System.out.println("\n--- BORRAR UN EMPLEADO ---");
        int employeeId;

        try {
            System.out.print("Introduzca el ID del empleado a eliminar: ");
            employeeId = Integer.parseInt(keyboard.nextLine());

            if (!controller.doesEmployeeExist(employeeId)) {
                System.out.println("\nERROR! No existe un empleado con el ID " + employeeId + "\n");
            } else {
                Optional<Employee> employeeOptional = controller.getEmployeeById(employeeId);
                if (employeeOptional.isPresent()) {
                    System.out.println("\nLa información del empleado a eliminar es:");
                    System.out.println(employeeOptional.get());

                    System.out.print("\n¿Está seguro de que desea eliminar este empleado? (S/N): ");
                    String confirmation = keyboard.nextLine().trim().toUpperCase();

                    if (confirmation.equals("S"))
                        isDeleted = controller.deleteEmployeeById(employeeId);
                }
            }

        } catch (SQLException e) {
            System.out.println("\nERROR SQL, no se ha podido eliminar el empleado del sistema\n");
        } catch (NumberFormatException e) {
            System.out.println("\nError! Debe ingresar un número entero.\n");
        }
        return isDeleted;
    }

    /**
     * Maneja la lógica para actualizar los datos de un empleado.
     * @param keyboard Scanner para leer desde consola.
     * @param controller Controlador del sistema de reservas.
     * @return true si el empleado se actualizó correctamente, false en caso contrario.
     */
    private static boolean handleUpdateEmployee(Scanner keyboard, ReservationSystemController controller) {
        boolean isUpdated = false;
        int employeeId;
        System.out.println("\n--- MODIFICAR DATOS DE EMPLEADO ---");
        try {
            System.out.print("Introduzca el ID del empleado a modificar: ");
            employeeId = Integer.parseInt(keyboard.nextLine());

            if (!controller.doesEmployeeExist(employeeId)) {
                System.out.println("\nERROR! No existe un empleado con el ID " + employeeId + "\n");
            } else {
                Optional<Employee> employeeOptional = controller.getEmployeeById(employeeId);

                if (employeeOptional.isPresent()) {
                    Employee employeeToUpdate = employeeOptional.get();
                    System.out.println("\nLa información del empleado es:");
                    System.out.println(employeeToUpdate);

                    System.out.println("\nIntroduzca los nuevos datos del empleado (deje en blanco para no modificar):");
                    String newDni = ReadInput.readString(keyboard, employeeToUpdate.getDni(), "el nuevo DNI del empleado", MAX_DNI_CHARS);
                    String newFirstName = ReadInput.readString(keyboard, employeeToUpdate.getFirstName(), "el nuevo nombre del empleado", MAX_NAME_CHARS);
                    String newLastName1 = ReadInput.readString(keyboard, employeeToUpdate.getLastName1(), "el nuevo primer apellido del empleado", MAX_NAME_CHARS);
                    String newLastName2 = ReadInput.readString(keyboard, employeeToUpdate.getLastName2(), "el nuevo segundo apellido del empleado", MAX_NAME_CHARS);
                    String newPhoneNumber = ReadInput.readStringNum(keyboard, employeeToUpdate.getPhoneNumber(), "el nuevo número de teléfono del empleado", MAX_PHONE_NUMBER_CHARS);
                    String newEmail = ReadInput.readString(keyboard, employeeToUpdate.getEmail(), "el nuevo email del empleado", MAX_NAME_CHARS);
                    String newDepartment = ReadInput.readString(keyboard, employeeToUpdate.getDepartment(), "el nuevo departamento del empleado", MAX_NAME_CHARS);

                    isUpdated = controller.updateInfoEmployee(employeeId, newDni, newFirstName, newLastName1, newLastName2, newPhoneNumber, newEmail, newDepartment);
                }
            }
        } catch (SQLException e) {
            System.out.println("\nERROR SQL, no se ha podido actualizar el empleado del sistema\n");
        } catch (NumberFormatException e) {
            System.out.println("\nError! Debe ingresar un número entero.\n");
        }
        return isUpdated;
    }

    /**
     * Muestra el listado de todos los empleados registrados en el sistema.
     * @param controller Controlador del sistema de reservas.
     */
    private static void handleSelectAllEmployees(ReservationSystemController controller) {
        try {
            ArrayList<Employee> employeesList = controller.getAllEmployees();
            if (employeesList.isEmpty()) {
                System.out.println("\nNo hay empleados registrados en el sistema.\n");
            } else {
                System.out.println("\n--- LISTADO DE EMPLEADOS ---");
                for (Employee employee : employeesList) {
                    System.out.println(employee);
                }
            }
        } catch (SQLException e) {
            System.out.println("\nERROR SQL, no se ha podido obtener el listado de empleados del sistema\n");
        }
    }

    /**
     * Devuelve el menú de gestión de empleados como String.
     * @return Menú de gestión de empleados.
     */
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
}
