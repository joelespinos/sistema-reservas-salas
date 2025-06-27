package model.dao;

import model.pojo.Reservation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface ReservationDAO {
    boolean insertNewReservation(Reservation reservationToCreate) throws SQLException;

    boolean deleteReservationById(int reservationId) throws SQLException;

    Optional<Reservation> getReservationById(int reservationId) throws SQLException;

    ArrayList<Reservation> getAllReservationsByRoomId(int roomId) throws SQLException;
}
