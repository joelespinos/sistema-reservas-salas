import controller.ReservationSystemController;
import model.pojo.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {

    ReservationSystemController controller;

    @BeforeEach
    void setUp() throws ClassNotFoundException {
        controller = new ReservationSystemController();
    }

    @Test
    void testInsertNewRoom() throws SQLException {
        String nombre = "Sala Test";
        int capacidad = 10;
        String recursos = "Proyector, Pizarra";
        boolean result = controller.insertNewRoom(nombre, capacidad, recursos);
        assertTrue(result);

        // Limpieza de residuos
        List<Room> rooms = controller.getAllRooms();
        for (Room room : rooms) {
            if (room.getName().equals(nombre) && room.getCapacity() == capacidad && room.getResources().equals(recursos)) {
                controller.deleteRoomById(room.getRoomId());
            }
        }
    }

    @Test
    void testDeleteRoom() throws SQLException {
        String nombre = "Sala Para Borrar";
        int capacidad = 5;
        String recursos = "TV";
        boolean insertResult = controller.insertNewRoom(nombre, capacidad, recursos);
        assertTrue(insertResult);

        List<Room> rooms = controller.getAllRooms();
        assertFalse(rooms.isEmpty());
        int roomId = rooms.get(rooms.size() - 1).getRoomId();

        boolean deleteResult = controller.deleteRoomById(roomId);
        assertTrue(deleteResult);

        // Limpieza de residuos (por si acaso)
        for (Room room : rooms) {
            if (room.getName().equals(nombre) && room.getCapacity() == capacidad && room.getResources().equals(recursos)) {
                controller.deleteRoomById(room.getRoomId());
            }
        }
    }

    @Test
    void testUpdateRoom() throws SQLException {
        String nombre = "Sala Para Actualizar";
        int capacidad = 8;
        String recursos = "Pizarra";
        boolean insertResult = controller.insertNewRoom(nombre, capacidad, recursos);
        assertTrue(insertResult);

        List<Room> rooms = controller.getAllRooms();
        assertFalse(rooms.isEmpty());
        int roomId = rooms.get(rooms.size() - 1).getRoomId();

        String nuevoNombre = "Sala Actualizada";
        int nuevaCapacidad = 12;
        String nuevosRecursos = "Proyector, TV";
        boolean updateResult = controller.updateInfoRoom(roomId, nuevoNombre, nuevaCapacidad, nuevosRecursos);
        assertTrue(updateResult);

        Optional<Room> updatedRoom = controller.getRoomById(roomId);
        assertTrue(updatedRoom.isPresent());
        assertEquals(nuevoNombre, updatedRoom.get().getName());
        assertEquals(nuevaCapacidad, updatedRoom.get().getCapacity());
        assertEquals(nuevosRecursos, updatedRoom.get().getResources());

        // Limpieza de residuos
        controller.deleteRoomById(roomId);
    }

    @Test
    void testGetRoomById() throws SQLException {
        String nombre = "Sala Consulta";
        int capacidad = 15;
        String recursos = "Pantalla";
        boolean insertResult = controller.insertNewRoom(nombre, capacidad, recursos);
        assertTrue(insertResult);

        List<Room> rooms = controller.getAllRooms();
        assertFalse(rooms.isEmpty());
        int roomId = rooms.get(rooms.size() - 1).getRoomId();

        Optional<Room> roomOptional = controller.getRoomById(roomId);
        assertTrue(roomOptional.isPresent());
        Room room = roomOptional.get();
        assertEquals(nombre, room.getName());
        assertEquals(capacidad, room.getCapacity());
        assertEquals(recursos, room.getResources());

        // Limpieza de residuos
        controller.deleteRoomById(roomId);
    }

    @Test
    void testGetAllRooms() throws SQLException {
        String nombre1 = "Sala 1";
        String nombre2 = "Sala 2";
        int capacidad1 = 5;
        int capacidad2 = 8;
        String recursos1 = "Proyector";
        String recursos2 = "Pizarra";
        controller.insertNewRoom(nombre1, capacidad1, recursos1);
        controller.insertNewRoom(nombre2, capacidad2, recursos2);

        List<Room> rooms = controller.getAllRooms();
        assertNotNull(rooms);
        assertTrue(rooms.size() >= 2);

        boolean foundSala1 = false;
        boolean foundSala2 = false;
        for (Room room : rooms) {
            if (nombre1.equals(room.getName())) foundSala1 = true;
            if (nombre2.equals(room.getName())) foundSala2 = true;
        }
        assertTrue(foundSala1);
        assertTrue(foundSala2);

        // Limpieza de residuos
        for (Room room : rooms) {
            if ((room.getName().equals(nombre1) && room.getCapacity() == capacidad1 && room.getResources().equals(recursos1)) ||
                (room.getName().equals(nombre2) && room.getCapacity() == capacidad2 && room.getResources().equals(recursos2))) {
                controller.deleteRoomById(room.getRoomId());
            }
        }
    }
}
