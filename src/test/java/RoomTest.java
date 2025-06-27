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
    }

    @Test
    void testDeleteRoom() throws SQLException {
        // Primero insertamos una sala para asegurarnos de que existe
        String nombre = "Sala Para Borrar";
        int capacidad = 5;
        String recursos = "TV";
        boolean insertResult = controller.insertNewRoom(nombre, capacidad, recursos);
        assertTrue(insertResult);

        // Suponemos que el metodo getAllRooms devuelve una lista y la última es la recién insertada
        List<Room> rooms = controller.getAllRooms();
        assertFalse(rooms.isEmpty());
        int roomId = rooms.get(rooms.size() - 1).getRoomId();

        boolean deleteResult = controller.deleteRoomById(roomId);
        assertTrue(deleteResult);
    }

    @Test
    void testUpdateRoom() throws SQLException {
        // Insertar una sala para actualizar
        String nombre = "Sala Para Actualizar";
        int capacidad = 8;
        String recursos = "Pizarra";
        boolean insertResult = controller.insertNewRoom(nombre, capacidad, recursos);
        assertTrue(insertResult);

        // Obtener el ID de la sala recién insertada
        List<Room> rooms = controller.getAllRooms();
        assertFalse(rooms.isEmpty());
        int roomId = rooms.get(rooms.size() - 1).getRoomId();

        // Actualizar la sala
        String nuevoNombre = "Sala Actualizada";
        int nuevaCapacidad = 12;
        String nuevosRecursos = "Proyector, TV";
        boolean updateResult = controller.updateInfoRoom(roomId, nuevoNombre, nuevaCapacidad, nuevosRecursos);
        assertTrue(updateResult);

        // Comprobar que los cambios se han aplicado
        Optional<Room> updatedRoom = controller.getRoomById(roomId);
        assertTrue(updatedRoom.isPresent());
        assertEquals(nuevoNombre, updatedRoom.get().getName());
        assertEquals(nuevaCapacidad, updatedRoom.get().getCapacity());
        assertEquals(nuevosRecursos, updatedRoom.get().getResources());
    }

    @Test
    void testGetRoomById() throws SQLException {
        // Insertar una sala para probar la obtención por ID
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
    }

    @Test
    void testGetAllRooms() throws SQLException {
        // Insertar dos salas para asegurar que hay al menos dos
        controller.insertNewRoom("Sala 1", 5, "Proyector");
        controller.insertNewRoom("Sala 2", 8, "Pizarra");

        List<Room> rooms = controller.getAllRooms();
        assertNotNull(rooms);
        assertTrue(rooms.size() >= 2);
        // Comprobar que hay al menos una sala con nombre "Sala 1" y otra con "Sala 2"
        boolean foundSala1 = false;
        boolean foundSala2 = false;
        for (Room room : rooms) {
            if ("Sala 1".equals(room.getName())) foundSala1 = true;
            if ("Sala 2".equals(room.getName())) foundSala2 = true;
        }
        assertTrue(foundSala1);
        assertTrue(foundSala2);
    }
}
