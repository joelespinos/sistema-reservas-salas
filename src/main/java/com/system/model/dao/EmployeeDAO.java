package com.system.model.dao;

import com.system.model.pojo.Employee;
import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDAO {

    boolean insertNewEmployee(Employee employeeToCreate) throws SQLException;

    boolean deleteEmployeeById(int employeeId) throws SQLException;

    boolean updateInfoEmployee(Employee employeeToUpdate) throws SQLException;

    Employee getEmployeeById(int employeeId) throws SQLException;

    ArrayList<Employee> getAllEmployees() throws SQLException;
}
