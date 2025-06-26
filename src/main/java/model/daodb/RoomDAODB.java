package model.daodb;

import model.dao.RoomDAO;
import model.pojo.Room;
import utils.DAODBConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class RoomDAODB implements RoomDAO {

    private static final int NON_ROWS_AFFECTED = 0;
    
    @Override
    public boolean insertNewRoom(Room roomToCreate) throws SQLException {
        boolean isInsert = false;
        String sqlSentence = "INSERT INTO Room(Name, Capacity, Resources) VALUES(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DAODBConstants.URL, DAODBConstants.USER, DAODBConstants.PASSWD);
             PreparedStatement psInsert = conn.prepareStatement(sqlSentence)) {

            psInsert.setString(1, roomToCreate.getName());
            psInsert.setInt(2, roomToCreate.getCapacity());
            psInsert.setString(3, roomToCreate.getResources());

            isInsert = psInsert.executeUpdate() > NON_ROWS_AFFECTED;
        }
        return isInsert;
    }

    @Override
    public boolean deleteRoomById(int roomId) throws SQLException {
        boolean isDeleted = false;
        String sqlSentence = "DELETE FROM Room WHERE Room_id = ?";

        try (Connection conn = DriverManager.getConnection(DAODBConstants.URL, DAODBConstants.USER, DAODBConstants.PASSWD);
             PreparedStatement psDelete = conn.prepareStatement(sqlSentence)) {

            psDelete.setInt(1, roomId);
            isDeleted = psDelete.executeUpdate() > NON_ROWS_AFFECTED;
        }
        return isDeleted;
    }

    @Override
    public boolean updateInfoRoom(Room roomToUpdate) throws SQLException {
        boolean isUpdated = false;
        String sqlSentence = "UPDATE Room SET Name = ?, Capacity = ?, Resources = ? WHERE Room_id = ?";
        try (Connection conn = DriverManager.getConnection(DAODBConstants.URL, DAODBConstants.USER, DAODBConstants.PASSWD);
             PreparedStatement psUpdate = conn.prepareStatement(sqlSentence)) {

            psUpdate.setString(1, roomToUpdate.getName());
            psUpdate.setInt(2, roomToUpdate.getCapacity());
            psUpdate.setString(3, roomToUpdate.getResources());
            psUpdate.setInt(4, roomToUpdate.getRoomId());

            isUpdated = psUpdate.executeUpdate() > NON_ROWS_AFFECTED;
        }
        return isUpdated;
    }

    @Override
    public Optional<Room> getRoomById(int roomId) throws SQLException {
        Optional<Room> roomOptional = Optional.empty();
        String sqlSentence = "SELECT * FROM Room WHERE Room_id = ?";

        try (Connection conn = DriverManager.getConnection(DAODBConstants.URL, DAODBConstants.USER, DAODBConstants.PASSWD);
             PreparedStatement psSelect = conn.prepareStatement(sqlSentence)) {

            psSelect.setInt(1, roomId);
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                Room room = new Room(rs.getInt("Room_id"),
                        rs.getString("Name"),
                        rs.getInt("Capacity"),
                        rs.getString("Resources"));
                roomOptional = Optional.of(room);
            }
        }
        return roomOptional;
    }

    @Override
    public ArrayList<Room> getAllRooms() throws SQLException {
        ArrayList<Room> rooms = new ArrayList<>();
        String sqlSentence = "SELECT * FROM Room";
        try (Connection conn = DriverManager.getConnection(DAODBConstants.URL, DAODBConstants.USER, DAODBConstants.PASSWD);
             PreparedStatement psSelect = conn.prepareStatement(sqlSentence);
             ResultSet rs = psSelect.executeQuery()) {

            while (rs.next()) {
                Room room = new Room(rs.getInt("Room_id"),
                        rs.getString("Name"),
                        rs.getInt("Capacity"),
                        rs.getString("Resources"));
                rooms.add(room);
            }
        }
        return rooms;
    }
}
