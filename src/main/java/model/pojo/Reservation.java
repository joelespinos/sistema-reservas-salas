package model.pojo;

import java.util.Objects;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private int reservationId;
    private int roomId;
    private int employeeId;
    private LocalDate reservationDate;
    private LocalTime startTime;
    private LocalTime endTime;

    // Constructor por defecto
    public Reservation() {
        this.reservationId = 0;
        this.roomId = 0;
        this.employeeId = 0;
        this.reservationDate = null;
        this.startTime = null;
        this.endTime = null;
    }

    // Constructor parametrizado
    public Reservation(int reservationId, int roomId, int employeeId, LocalDate reservationDate, LocalTime startTime, LocalTime endTime) {
        this.reservationId = reservationId;
        this.roomId = roomId;
        this.employeeId = employeeId;
        this.reservationDate = reservationDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Constructor parametrizado sin reservationId para inserciones
    public Reservation(int roomId, int employeeId, LocalDate reservationDate, LocalTime startTime, LocalTime endTime) {
        this.reservationId = 0;
        this.roomId = roomId;
        this.employeeId = employeeId;
        this.reservationDate = reservationDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Constructor copia
    public Reservation(Reservation other) {
        this.reservationId = other.reservationId;
        this.roomId = other.roomId;
        this.employeeId = other.employeeId;
        this.reservationDate = other.reservationDate;
        this.startTime = other.startTime;
        this.endTime = other.endTime;
    }

    // Getters y setters
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    // equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return reservationId == that.reservationId &&
                roomId == that.roomId &&
                employeeId == that.employeeId &&
                Objects.equals(reservationDate, that.reservationDate) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }

    // hashCode
    @Override
    public int hashCode() {
        return Objects.hash(reservationId, roomId, employeeId, reservationDate, startTime, endTime);
    }

    // toString
    @Override
    public String toString() {
        return  "Reserva ID: " + reservationId + "\n" +
                "Sala ID: " + roomId + "\n" +
                "Empleado ID: " + employeeId + "\n" +
                "Fecha: " + reservationDate + "\n" +
                "Hora inicio: " + startTime + "\n" +
                "Hora fin: " + endTime;
    }
}
