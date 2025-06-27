import controller.ReservationSystemController;
import model.pojo.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationTest {

    ReservationSystemController controller;

    @BeforeEach
    void setUp() throws ClassNotFoundException {
        controller = new ReservationSystemController();
    }

    @Test
    void testInsertNewReservation() throws SQLException {
        // Primero insertamos una sala y un empleado para asegurar que existen
        String roomName = "Sala Reserva Test";
        int roomCapacity = 10;
        String roomResources = "Proyector";
        boolean roomResult = controller.insertNewRoom(roomName, roomCapacity, roomResources);
        assertTrue(roomResult);

        String dni = "99999999Z";
        String firstName = "EmpleadoReserva";
        String lastName1 = "Test";
        String lastName2 = "Apellido2";
        String phoneNumber = "699999999";
        String email = "empleadoreserva@test.com";
        String department = "Reservas";
        boolean employeeResult = controller.insertNewEmployee(dni, firstName, lastName1, lastName2, phoneNumber, email, department);
        assertTrue(employeeResult);

        // Obtener los IDs recién insertados
        List<model.pojo.Room> rooms = controller.getAllRooms();
        int roomId = rooms.get(rooms.size() - 1).getRoomId();

        List<model.pojo.Employee> employees = controller.getAllEmployees();
        int employeeId = employees.get(employees.size() - 1).getEmployeeId();

        // Insertar la reserva
        LocalDate reservationDate = LocalDate.now().plusDays(1);
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(11, 0);

        boolean reservationResult = controller.insertNewReservation(roomId, employeeId, reservationDate, startTime, endTime);
        assertTrue(reservationResult);
    }
}
