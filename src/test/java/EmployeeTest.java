import controller.ReservationSystemController;
import model.pojo.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    ReservationSystemController controller;

    @BeforeEach
    void setUp() throws ClassNotFoundException {
        controller = new ReservationSystemController();
    }

    @Test
    void testInsertNewEmployee() throws SQLException {
        String dni = "12345678A";
        String firstName = "Empleado";
        String lastName1 = "Test";
        String lastName2 = "Apellido2";
        String phoneNumber = "600000000";
        String email = "empleado@test.com";
        String department = "IT";
        boolean result = controller.insertNewEmployee(dni, firstName, lastName1, lastName2, phoneNumber, email, department);
        assertTrue(result);

        // Limpieza de residuos
        List<Employee> employees = controller.getAllEmployees();
        for (Employee emp : employees) {
            if (emp.getDni().equals(dni) && emp.getEmail().equals(email)) {
                controller.deleteEmployeeById(emp.getEmployeeId());
            }
        }
    }

    @Test
    void testDeleteEmployee() throws SQLException {
        String dni = "87654321B";
        String firstName = "Empleado";
        String lastName1 = "Borrar";
        String lastName2 = "Apellido2";
        String phoneNumber = "611111111";
        String email = "borrar@test.com";
        String department = "HR";
        boolean insertResult = controller.insertNewEmployee(dni, firstName, lastName1, lastName2, phoneNumber, email, department);
        assertTrue(insertResult);

        List<Employee> employees = controller.getAllEmployees();
        assertFalse(employees.isEmpty());
        int employeeId = employees.get(employees.size() - 1).getEmployeeId();

        boolean deleteResult = controller.deleteEmployeeById(employeeId);
        assertTrue(deleteResult);

        // Limpieza de residuos (por si acaso)
        for (Employee emp : employees) {
            if (emp.getDni().equals(dni) && emp.getEmail().equals(email)) {
                controller.deleteEmployeeById(emp.getEmployeeId());
            }
        }
    }

    @Test
    void testUpdateEmployee() throws SQLException {
        String dni = "11223344C";
        String firstName = "Empleado";
        String lastName1 = "Actualizar";
        String lastName2 = "Apellido2";
        String phoneNumber = "622222222";
        String email = "actualizar@test.com";
        String department = "Ventas";
        boolean insertResult = controller.insertNewEmployee(dni, firstName, lastName1, lastName2, phoneNumber, email, department);
        assertTrue(insertResult);

        List<Employee> employees = controller.getAllEmployees();
        assertFalse(employees.isEmpty());
        int employeeId = employees.get(employees.size() - 1).getEmployeeId();

        String newDni = "11223344D";
        String newFirstName = "EmpleadoActualizado";
        String newLastName1 = "Actualizado1";
        String newLastName2 = "Actualizado2";
        String newPhoneNumber = "633333333";
        String newEmail = "actualizado@test.com";
        String newDepartment = "Finanzas";
        boolean updateResult = controller.updateInfoEmployee(employeeId, newDni, newFirstName, newLastName1, newLastName2, newPhoneNumber, newEmail, newDepartment);
        assertTrue(updateResult);

        Optional<Employee> updatedEmployee = controller.getEmployeeById(employeeId);
        assertTrue(updatedEmployee.isPresent());
        Employee emp = updatedEmployee.get();
        assertEquals(newDni, emp.getDni());
        assertEquals(newFirstName, emp.getFirstName());
        assertEquals(newLastName1, emp.getLastName1());
        assertEquals(newLastName2, emp.getLastName2());
        assertEquals(newPhoneNumber, emp.getPhoneNumber());
        assertEquals(newEmail, emp.getEmail());
        assertEquals(newDepartment, emp.getDepartment());

        // Limpieza de residuos
        controller.deleteEmployeeById(employeeId);
    }

    @Test
    void testGetEmployeeById() throws SQLException {
        String dni = "55667788E";
        String firstName = "Empleado";
        String lastName1 = "Consulta";
        String lastName2 = "Apellido2";
        String phoneNumber = "644444444";
        String email = "consulta@test.com";
        String department = "Soporte";
        boolean insertResult = controller.insertNewEmployee(dni, firstName, lastName1, lastName2, phoneNumber, email, department);
        assertTrue(insertResult);

        List<Employee> employees = controller.getAllEmployees();
        assertFalse(employees.isEmpty());
        int employeeId = employees.get(employees.size() - 1).getEmployeeId();

        Optional<Employee> employeeOptional = controller.getEmployeeById(employeeId);
        assertTrue(employeeOptional.isPresent());
        Employee emp = employeeOptional.get();
        assertEquals(dni, emp.getDni());
        assertEquals(firstName, emp.getFirstName());
        assertEquals(lastName1, emp.getLastName1());
        assertEquals(lastName2, emp.getLastName2());
        assertEquals(phoneNumber, emp.getPhoneNumber());
        assertEquals(email, emp.getEmail());
        assertEquals(department, emp.getDepartment());

        // Limpieza de residuos
        controller.deleteEmployeeById(employeeId);
    }

    @Test
    void testGetAllEmployees() throws SQLException {
        String dni1 = "11111111F";
        String dni2 = "22222222G";
        String email1 = "empleado1@test.com";
        String email2 = "empleado2@test.com";
        controller.insertNewEmployee(dni1, "Empleado1", "Apellido1", "Apellido2", "655555555", email1, "Compras");
        controller.insertNewEmployee(dni2, "Empleado2", "Apellido1", "Apellido2", "666666666", email2, "Logística");

        List<Employee> employees = controller.getAllEmployees();
        assertNotNull(employees);
        assertTrue(employees.size() >= 2);

        boolean foundEmp1 = false;
        boolean foundEmp2 = false;
        for (Employee employee : employees) {
            if ("Empleado1".equals(employee.getFirstName()) && dni1.equals(employee.getDni())) foundEmp1 = true;
            if ("Empleado2".equals(employee.getFirstName()) && dni2.equals(employee.getDni())) foundEmp2 = true;
        }
        assertTrue(foundEmp1);
        assertTrue(foundEmp2);

        // Limpieza de residuos
        for (Employee emp : employees) {
            if ((emp.getDni().equals(dni1) && emp.getEmail().equals(email1)) ||
                (emp.getDni().equals(dni2) && emp.getEmail().equals(email2))) {
                controller.deleteEmployeeById(emp.getEmployeeId());
            }
        }
    }
}
