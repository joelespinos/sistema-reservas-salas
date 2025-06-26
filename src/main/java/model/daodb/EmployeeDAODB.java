package model.daodb;

import model.dao.EmployeeDAO;
import model.pojo.Employee;
import utils.DAODBConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class EmployeeDAODB implements EmployeeDAO {
    private static final int NON_ROWS_AFFECTED = 0;

    @Override
    public boolean insertNewEmployee(Employee employeeToCreate) throws SQLException {
        boolean isInsert = false;
        String sqlSentence = "INSERT INTO Employee(DNI, First_name, Last_name1, Last_name2, Phone_number, Email, Department) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DAODBConstants.URL, DAODBConstants.USER, DAODBConstants.PASSWD);
             PreparedStatement psInsert = conn.prepareStatement(sqlSentence)) {

            psInsert.setString(1, employeeToCreate.getDni());
            psInsert.setString(2, employeeToCreate.getFirstName());
            psInsert.setString(3, employeeToCreate.getLastName1());
            psInsert.setString(4, employeeToCreate.getLastName2());
            psInsert.setString(5, employeeToCreate.getPhoneNumber());
            psInsert.setString(6, employeeToCreate.getEmail());
            psInsert.setString(7, employeeToCreate.getDepartment());

            isInsert = psInsert.executeUpdate() > NON_ROWS_AFFECTED;
        }
        return isInsert;
    }

    @Override
    public boolean deleteEmployeeById(int employeeId) throws SQLException {
        boolean isDeleted = false;
        String sqlSentence = "DELETE FROM Employee WHERE Employee_id = ?";
        try (Connection conn = DriverManager.getConnection(DAODBConstants.URL, DAODBConstants.USER, DAODBConstants.PASSWD);
             PreparedStatement psDelete = conn.prepareStatement(sqlSentence)) {

            psDelete.setInt(1, employeeId);
            isDeleted = psDelete.executeUpdate() > NON_ROWS_AFFECTED;
        }
        return isDeleted;
    }

    @Override
    public boolean updateInfoEmployee(Employee employeeToUpdate) throws SQLException {
        boolean isUpdated = false;
        String sqlSentence = "UPDATE Employee SET DNI = ?, First_name = ?, Last_name1 = ?, Last_name2 = ?, Phone_number = ?, Email = ?, Department = ? WHERE Employee_id = ?";
        try (Connection conn = DriverManager.getConnection(DAODBConstants.URL, DAODBConstants.USER, DAODBConstants.PASSWD);
             PreparedStatement psUpdate = conn.prepareStatement(sqlSentence)) {

            psUpdate.setString(1, employeeToUpdate.getDni());
            psUpdate.setString(2, employeeToUpdate.getFirstName());
            psUpdate.setString(3, employeeToUpdate.getLastName1());
            psUpdate.setString(4, employeeToUpdate.getLastName2());
            psUpdate.setString(5, employeeToUpdate.getPhoneNumber());
            psUpdate.setString(6, employeeToUpdate.getEmail());
            psUpdate.setString(7, employeeToUpdate.getDepartment());
            psUpdate.setInt(8, employeeToUpdate.getEmployeeId());

            isUpdated = psUpdate.executeUpdate() > NON_ROWS_AFFECTED;
        }
        return isUpdated;
    }

    @Override
    public Optional<Employee> getEmployeeById(int employeeId) throws SQLException {
        Optional<Employee> employeeOptional = Optional.empty();
        String sqlSentence = "SELECT * FROM Employee WHERE Employee_id = ?";
        try (Connection conn = DriverManager.getConnection(DAODBConstants.URL, DAODBConstants.USER, DAODBConstants.PASSWD);
             PreparedStatement psSelect = conn.prepareStatement(sqlSentence)) {

            psSelect.setInt(1, employeeId);
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("Employee_id"),
                        rs.getString("DNI"),
                        rs.getString("First_name"),
                        rs.getString("Last_name1"),
                        rs.getString("Last_name2"),
                        rs.getString("Phone_number"),
                        rs.getString("Email"),
                        rs.getString("Department")
                );
                employeeOptional = Optional.of(employee);
            }
        }
        return employeeOptional;
    }

    @Override
    public ArrayList<Employee> getAllEmployees() throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();
        String sqlSentence = "SELECT * FROM Employee";
        try (Connection conn = DriverManager.getConnection(DAODBConstants.URL, DAODBConstants.USER, DAODBConstants.PASSWD);
             PreparedStatement psSelect = conn.prepareStatement(sqlSentence);
             ResultSet rs = psSelect.executeQuery()) {

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("Employee_id"),
                        rs.getString("DNI"),
                        rs.getString("First_name"),
                        rs.getString("Last_name1"),
                        rs.getString("Last_name2"),
                        rs.getString("Phone_number"),
                        rs.getString("Email"),
                        rs.getString("Department")
                );
                employees.add(employee);
            }
        }
        return employees;
    }
}
