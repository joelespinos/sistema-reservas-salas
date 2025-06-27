import controller.ReservationSystemController;
import model.pojo.Reservation;
import model.pojo.Employee;
import model.pojo.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {

    ReservationSystemController controller;

    @BeforeEach
    void setUp() throws ClassNotFoundException {
        controller = new ReservationSystemController();
    }

    @Test
    void testDeleteReservation() throws SQLException {
        // Crear sala
        String roomName = "Sala Test Delete";
        int roomCapacity = 5;
        String roomResources = "Proyector";
        assertTrue(controller.insertNewRoom(roomName, roomCapacity, roomResources));
        List<Room> rooms = controller.getAllRooms();
        int roomId = rooms.get(rooms.size() - 1).getRoomId();

        // Crear empleado
        String dni = "12345678Z";
        String firstName = "Empleado";
        String lastName1 = "Delete";
        String lastName2 = "Test";
        String phoneNumber = "600000001";
        String email = "empleadodelete@test.com";
        String department = "TestDept";
        assertTrue(controller.insertNewEmployee(dni, firstName, lastName1, lastName2, phoneNumber, email, department));
        List<Employee> employees = controller.getAllEmployees();
        int employeeId = employees.get(employees.size() - 1).getEmployeeId();

        // Crear reserva
        LocalDate reservationDate = LocalDate.now().plusDays(1);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);
        assertTrue(controller.insertNewReservation(roomId, employeeId, reservationDate, startTime, endTime));

        // Buscar la reserva recién creada
        Optional<Reservation> reservationOpt = Optional.empty();
        for (int i = 1; i <= 1000; i++) { // Intentar buscar por los últimos 1000 posibles IDs
            Optional<Reservation> r = controller.getReservationById(i);
            if (r.isPresent()) {
                Reservation res = r.get();
                if (res.getRoomId() == roomId && res.getEmployeeId() == employeeId &&
                    res.getReservationDate().equals(reservationDate) &&
                    res.getStartTime().equals(startTime) && res.getEndTime().equals(endTime)) {
                    reservationOpt = Optional.of(res);
                    break;
                }
            }
        }
        assertTrue(reservationOpt.isPresent());
        int reservationId = reservationOpt.get().getReservationId();

        // Borrar la reserva
        assertTrue(controller.deleteReservationById(reservationId));

        // Eliminar residuos
        controller.deleteEmployeeById(employeeId);
        controller.deleteRoomById(roomId);
    }
}
