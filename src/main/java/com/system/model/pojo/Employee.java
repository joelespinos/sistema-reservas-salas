package com.system.model.pojo;

import java.util.Objects;

public class Employee {
    private int employeeId;
    private String dni;
    private String firstName;
    private String lastName1;
    private String lastName2;
    private String phoneNumber;
    private String email;
    private String department;

    // Constructor por defecto
    public Employee() {
        this.employeeId = 0;
        this.dni = "";
        this.firstName = "";
        this.lastName1 = "";
        this.lastName2 = "";
        this.phoneNumber = "";
        this.email = "";
        this.department = "";
    }

    // Constructor parametrizado
    public Employee(int employeeId, String dni, String firstName, String lastName1, String lastName2, String phoneNumber, String email, String department) {
        this.employeeId = employeeId;
        this.dni = dni;
        this.firstName = firstName;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.department = department;
    }

    // Constructor copia
    public Employee(Employee other) {
        this.employeeId = other.employeeId;
        this.dni = other.dni;
        this.firstName = other.firstName;
        this.lastName1 = other.lastName1;
        this.lastName2 = other.lastName2;
        this.phoneNumber = other.phoneNumber;
        this.email = other.email;
        this.department = other.department;
    }

    // Getters y setters
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName1() {
        return lastName1;
    }

    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId == employee.employeeId &&
                Objects.equals(dni, employee.dni) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName1, employee.lastName1) &&
                Objects.equals(lastName2, employee.lastName2) &&
                Objects.equals(phoneNumber, employee.phoneNumber) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(department, employee.department);
    }

    // hashCode
    @Override
    public int hashCode() {
        return Objects.hash(employeeId, dni, firstName, lastName1, lastName2, phoneNumber, email, department);
    }

    // toString
    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", dni='" + dni + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName1='" + lastName1 + '\'' +
                ", lastName2='" + lastName2 + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
