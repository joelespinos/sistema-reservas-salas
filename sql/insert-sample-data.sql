-- Descripción: Script para insertar datos de ejemplo en las tablas del sistema de reservas de salas.
-- Base de datos: reservations-system
-- Autor: [Joel Espinós Ruiz]

USE `reservations-system`;

-- Insertar empleados
INSERT INTO Employee (DNI, First_name, Last_name1, Last_name2, Phone_number, Email, Department) VALUES
('12345678A', 'Ana', 'Gomez', 'Lopez', '612345678', 'ana.gomez@email.com', 'IT'),
('22345678B', 'Luis', 'Martinez', 'Perez', '622345678', 'luis.martinez@email.com', 'HR'),
('32345678C', 'Clara', 'Rodriguez', 'Diaz', '632345678', 'clara.rod@email.com', 'Finance'),
('42345678D', 'Mario', 'Lopez', 'Garcia', '642345678', 'mario.lopez@email.com', 'Marketing'),
('52345678E', 'Lucia', 'Fernandez', 'Vega', '652345678', 'lucia.fernandez@email.com', 'Sales'),
('62345678F', 'Carlos', 'Sanchez', 'Ruiz', '662345678', 'carlos.sanchez@email.com', 'IT'),
('72345678G', 'Elena', 'Jimenez', 'Moreno', '672345678', 'elena.jimenez@email.com', 'Support'),
('82345678H', 'Raul', 'Ramirez', 'Castro', '682345678', 'raul.ramirez@email.com', 'Logistics'),
('92345678I', 'Ines', 'Navas', 'Ortiz', '692345678', 'ines.navas@email.com', 'Admin'),
('03345678J', 'David', 'Paredes', 'Cano', '712345678', 'david.paredes@email.com', 'R&D');

-- Insertar salas
INSERT INTO Room (Name, Capacity, Resources) VALUES
('Sala Reuniones A', 10, 'Proyector, Pizarra'),
('Sala Reuniones B', 8, 'Pantalla, HDMI'),
('Sala Proyectos 1', 12, 'TV, USB-C'),
('Sala Proyectos 2', 6, 'Pizarra'),
('Sala Conferencias', 15, 'Videoconferencia'),
('Sala Desarrollo', 20, 'Proyector 4K, Audio'),
('Sala Dirección', 10, 'TV, HDMI'),
('Sala Formación', 5, 'Pizarra, Auriculares'),
('Sala Creativa', 9, 'Pantalla táctil'),
('Sala Técnica', 7, 'HDMI, Proyector portátil');

-- Insertar reservas (con fechas no solapadas)
INSERT INTO Reservation (Room_id, Employee_id, Reservation_date, Start_time, End_time) VALUES
-- Pasadas
(1, 1, '2024-06-01', '09:00:00', '10:00:00'),
(1, 2, '2024-06-01', '10:30:00', '11:30:00'),
(2, 3, '2024-06-02', '08:00:00', '09:00:00'),
(3, 4, '2024-06-03', '13:00:00', '14:00:00'),
(4, 5, '2024-06-04', '15:00:00', '16:00:00'),
(5, 6, '2024-06-05', '09:00:00', '10:00:00'),
(6, 7, '2024-06-06', '11:00:00', '12:00:00'),
(7, 8, '2024-06-07', '14:00:00', '15:00:00'),
(8, 9, '2024-06-08', '16:00:00', '17:00:00'),
(9, 10, '2024-06-09', '10:00:00', '11:00:00'),

-- Futuras
(1, 3, '2025-07-01', '09:00:00', '10:00:00'),
(1, 4, '2025-07-01', '10:30:00', '11:30:00'),
(2, 5, '2025-07-02', '09:00:00', '10:00:00'),
(3, 6, '2025-07-03', '08:00:00', '09:00:00'),
(4, 7, '2025-07-04', '13:00:00', '14:00:00'),
(5, 8, '2025-07-05', '14:30:00', '15:30:00'),
(6, 9, '2025-07-06', '09:00:00', '10:00:00'),
(7, 10, '2025-07-07', '10:30:00', '11:30:00'),
(8, 1, '2025-07-08', '11:00:00', '12:00:00'),
(9, 2, '2025-07-09', '13:00:00', '14:00:00'),
(10, 3, '2025-07-10', '15:00:00', '16:00:00'),
(2, 4, '2025-07-11', '09:00:00', '10:00:00'),
(3, 5, '2025-07-11', '10:30:00', '11:30:00'),
(4, 6, '2025-07-11', '12:00:00', '13:00:00'),
(5, 7, '2025-07-11', '14:00:00', '15:00:00');
