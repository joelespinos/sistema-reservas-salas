package controller;

import model.daodb.EmployeeDAODB;
import model.daodb.RoomDAODB;
import model.pojo.Room;
import model.pojo.Employee;
import utils.DAODBConstants;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ReservationSystemController {

    private RoomDAODB roomManager;
    private EmployeeDAODB employeeManager;
    // private ReservationDAODB reservationManager;

    public ReservationSystemController() throws ClassNotFoundException {
        Class.forName(DAODBConstants.DRIVER);
        this.roomManager = new RoomDAODB();
        this.employeeManager = new EmployeeDAODB();
        // this.reservationManager = new ReservationDAODB();
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

    public Room getRoomById(int roomId) throws SQLException {
        return roomManager.getRoomById(roomId).get();
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

    public Employee getEmployeeById(int employeeId) throws SQLException {
        return employeeManager.getEmployeeById(employeeId).get();
    }

    public boolean doesEmployeeExist(int employeeId) throws SQLException {
        return employeeManager.getEmployeeById(employeeId).isPresent();
    }

    public ArrayList<Employee> getAllEmployees() throws SQLException {
        return employeeManager.getAllEmployees();
    }
}
