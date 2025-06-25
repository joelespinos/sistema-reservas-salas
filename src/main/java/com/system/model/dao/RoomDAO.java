package com.system.model.dao;

import com.system.model.pojo.Room;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface RoomDAO {

    boolean insertNewRoom(Room roomToCreate) throws SQLException;

    boolean deleteRoomById(int roomId) throws SQLException;

    boolean updateInfoRoom(Room roomToUpdate) throws SQLException;

    Optional<Room> getRoomById(int roomId) throws SQLException;

    ArrayList<Room> getAllRooms() throws SQLException;
}
