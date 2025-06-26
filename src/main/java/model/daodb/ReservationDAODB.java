package model.daodb;

import model.dao.ReservationDAO;
import model.pojo.Reservation;
import utils.DAODBConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class ReservationDAODB implements ReservationDAO {

    private static final int NON_ROWS_AFFECTED = 0;

    @Override
    public boolean insertNewReservation(Reservation reservationToCreate) throws SQLException {
        boolean isInserted = false;
        String sqlSentence = "INSERT INTO Reservation(Room_id, Employee_id, Reservation_date, Start_time, End_time) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DAODBConstants.URL, DAODBConstants.USER, DAODBConstants.PASSWD);
             PreparedStatement psInsert = conn.prepareStatement(sqlSentence)) {

            psInsert.setInt(1, reservationToCreate.getRoomId());
            psInsert.setInt(2, reservationToCreate.getEmployeeId());
            psInsert.setDate(3, java.sql.Date.valueOf(reservationToCreate.getReservationDate()));
            psInsert.setTime(4, java.sql.Time.valueOf(reservationToCreate.getStartTime()));
            psInsert.setTime(5, java.sql.Time.valueOf(reservationToCreate.getEndTime()));

            isInserted = psInsert.executeUpdate() > NON_ROWS_AFFECTED;
        }
        return isInserted;
    }

    @Override
    public boolean deleteReservationById(int reservationId) throws SQLException {
        boolean isDeleted = false;
        String sqlSentence = "DELETE FROM Reservation WHERE Reservation_id = ?";
        try (Connection conn = DriverManager.getConnection(DAODBConstants.URL, DAODBConstants.USER, DAODBConstants.PASSWD);
             PreparedStatement psDelete = conn.prepareStatement(sqlSentence)) {

            psDelete.setInt(1, reservationId);
            isDeleted = psDelete.executeUpdate() > NON_ROWS_AFFECTED;
        }
        return isDeleted;
    }

    @Override
    public Optional<Reservation> getReservationById(int reservationId) throws SQLException {
        Optional<Reservation> reservationOptional = Optional.empty();
        String sqlSentence = "SELECT * FROM Reservation WHERE Reservation_id = ?";
        try (Connection conn = DriverManager.getConnection(DAODBConstants.URL, DAODBConstants.USER, DAODBConstants.PASSWD);
             PreparedStatement psSelect = conn.prepareStatement(sqlSentence)) {

            psSelect.setInt(1, reservationId);
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                Reservation reservation = new Reservation(
                        rs.getInt("Reservation_id"),
                        rs.getInt("Room_id"),
                        rs.getInt("Employee_id"),
                        rs.getDate("Reservation_date").toLocalDate(),
                        rs.getTime("Start_time").toLocalTime(),
                        rs.getTime("End_time").toLocalTime()
                );
                reservationOptional = Optional.of(reservation);
            }
        }
        return reservationOptional;
    }

    @Override
    public ArrayList<Reservation> getAllReservationsByRoomId(int roomId) throws SQLException {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String sqlSentence = "SELECT * FROM Reservation WHERE Room_id = ?";
        try (Connection conn = DriverManager.getConnection(DAODBConstants.URL, DAODBConstants.USER, DAODBConstants.PASSWD);
             PreparedStatement psSelect = conn.prepareStatement(sqlSentence)) {

            psSelect.setInt(1, roomId);
            ResultSet rs = psSelect.executeQuery();

            while (rs.next()) {
                Reservation reservation = new Reservation(
                        rs.getInt("Reservation_id"),
                        rs.getInt("Room_id"),
                        rs.getInt("Employee_id"),
                        rs.getDate("Reservation_date").toLocalDate(),
                        rs.getTime("Start_time").toLocalTime(),
                        rs.getTime("End_time").toLocalTime()
                );
                reservations.add(reservation);
            }
        }
        return reservations;
    }
}
