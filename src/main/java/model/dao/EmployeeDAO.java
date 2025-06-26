package model.dao;

import model.pojo.Employee;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface EmployeeDAO {

    boolean insertNewEmployee(Employee employeeToCreate) throws SQLException;

    boolean deleteEmployeeById(int employeeId) throws SQLException;

    boolean updateInfoEmployee(Employee employeeToUpdate) throws SQLException;

    Optional<Employee> getEmployeeById(int employeeId) throws SQLException;

    ArrayList<Employee> getAllEmployees() throws SQLException;
}
