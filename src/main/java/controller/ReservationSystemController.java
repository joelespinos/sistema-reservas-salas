package controller;

import model.daodb.EmployeeDAODB;
import model.daodb.ReservationDAODB;
import model.daodb.RoomDAODB;
import model.pojo.Reservation;
import model.pojo.Room;
import model.pojo.Employee;
import utils.DAODBConstants;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class ReservationSystemController {

    private RoomDAODB roomManager;
    private EmployeeDAODB employeeManager;
    private ReservationDAODB reservationManager;

    public ReservationSystemController() throws ClassNotFoundException {
        Class.forName(DAODBConstants.DRIVER);
        this.roomManager = new RoomDAODB();
        this.employeeManager = new EmployeeDAODB();
        this.reservationManager = new ReservationDAODB();
    }

    // Métodos para Room

    public boolean insertNewRoom(String name, int capacity, String resources) throws SQLException {
        return roomManager.insertNewRoom(new Room(name, capacity, resources));
    }

    public boolean deleteRoomById(int roomId) throws SQLException {
        return roomManager.deleteRoomById(roomId);
    }

    public boolean updateInfoRoom(int roomId, String name, int capacity, String resources) throws SQLException {
        return roomManager.updateInfoRoom(new Room(roomId, name, capacity, resources));
    }

    public ArrayList<Room> getAllRooms() throws SQLException {
        return roomManager.getAllRooms();
    }

    public boolean doesRoomExist(int roomId) throws SQLException {
        return roomManager.getRoomById(roomId).isPresent();
    }

    public Optional<Room> getRoomById(int roomId) throws SQLException {
        return roomManager.getRoomById(roomId);
    }

    // Métodos para Employee

    public boolean insertNewEmployee(String dni, String firstName, String lastName1, String lastName2, String phoneNumber, String email, String department) throws SQLException {
        Employee employee = new Employee(dni, firstName, lastName1, lastName2, phoneNumber, email, department);
        return employeeManager.insertNewEmployee(employee);
    }

    public boolean deleteEmployeeById(int employeeId) throws SQLException {
        return employeeManager.deleteEmployeeById(employeeId);
    }

    public boolean updateInfoEmployee(int employeeId, String dni, String firstName, String lastName1, String lastName2, String phoneNumber, String email, String department) throws SQLException {
        Employee employee = new Employee(employeeId, dni, firstName, lastName1, lastName2, phoneNumber, email, department);
        return employeeManager.updateInfoEmployee(employee);
    }

    public Optional<Employee> getEmployeeById(int employeeId) throws SQLException {
        return employeeManager.getEmployeeById(employeeId);
    }

    public boolean doesEmployeeExist(int employeeId) throws SQLException {
        return employeeManager.getEmployeeById(employeeId).isPresent();
    }

    public ArrayList<Employee> getAllEmployees() throws SQLException {
        return employeeManager.getAllEmployees();
    }

    public boolean doesDNIExist(String dni) throws SQLException {
        return employeeManager.doesDNIExist(dni);
    }

    // Métodos para Reservation

    public boolean insertNewReservation(int roomId, int employeeId, LocalDate reservationDate, LocalTime startTime, LocalTime endTime) throws SQLException {
        return reservationManager.insertNewReservation(new Reservation(roomId, employeeId, reservationDate, startTime, endTime));
    }

    public boolean deleteReservationById(int reservationId) throws SQLException {
        return reservationManager.deleteReservationById(reservationId);
    }

    public Optional<Reservation> getReservationById(int reservationId) throws SQLException {
        return reservationManager.getReservationById(reservationId);
    }

    public Optional<Integer> validateReservationTime(int roomId, LocalDate reservationDate, LocalTime startTime, LocalTime endTime) throws SQLException {
        Optional<Integer> reservationIdOverlap = Optional.empty();
        ArrayList<Reservation> reservations = reservationManager.getAllReservationsByRoomId(roomId);
        int i = 0;

        while (i < reservations.size() && reservationIdOverlap.isEmpty()) {
            Reservation indexReservation = reservations.get(i);

            //Revisa que la fecha de la reserva sea la misma que la que se está intentando reservar
            if (indexReservation.getReservationDate().equals(reservationDate)) {

                if (startTime.isBefore(indexReservation.getEndTime()) &&
                        endTime.isAfter(indexReservation.getStartTime())) {
                    reservationIdOverlap = Optional.of(indexReservation.getReservationId());
                }
            }
            i++;
        }
        return reservationIdOverlap;
    }

    public boolean doesReservationExist(int reservationId) throws SQLException {
        return reservationManager.getReservationById(reservationId).isPresent();
    }

}
