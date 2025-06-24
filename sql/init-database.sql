-- Base de datos: reservations-system
-- Descripción: Script para crear la base de datos y las tablas necesarias para el sistema de reservas de salas, incluyendo empleados, salas y reservas.
-- Autor: [Joel Espinós Ruiz]

CREATE DATABASE IF NOT EXISTS `reservations-system`;
USE `reservations-system`;

-- Tabla: Employee (Empleado)
CREATE TABLE Employee (
    Employee_id INT PRIMARY KEY AUTO_INCREMENT,
    DNI VARCHAR(9) UNIQUE NOT NULL,
    First_name VARCHAR(40) NOT NULL,
    Last_name1 VARCHAR(40) NOT NULL,
    Last_name2 VARCHAR(40),
    Phone_number VARCHAR(9) NOT NULL,
    Email VARCHAR(40) UNIQUE NOT NULL,
    Department VARCHAR(40) NOT NULL
);

-- Tabla: Room (Sala)
CREATE TABLE Room (
    Room_id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(40) NOT NULL,
    Capacity INT NOT NULL,
    Resources TEXT
);

-- Tabla: Reservation (Reserva)
CREATE TABLE Reservation (
    Reservation_id INT PRIMARY KEY AUTO_INCREMENT,
    Room_id INT NOT NULL,
    Employee_id INT NOT NULL,
    Reservation_date DATE NOT NULL,
    Start_time TIME NOT NULL,
    End_time TIME NOT NULL,
    FOREIGN KEY (Room_id) REFERENCES Room(Room_id) ON DELETE CASCADE,
    FOREIGN KEY (Employee_id) REFERENCES Employee(Employee_id) ON DELETE CASCADE
);
