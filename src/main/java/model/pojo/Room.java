package model.pojo;

import java.util.Objects;

public class Room {
    private int roomId;
    private String name;
    private int capacity;
    private String resources;

    // Constructor por defecto
    public Room() {
        this.roomId = 0;
        this.name = "";
        this.capacity = 0;
        this.resources = "";
    }

    // Constructor parametrizado
    public Room(int roomId, String name, int capacity, String resources) {
        this.roomId = roomId;
        this.name = name;
        this.capacity = capacity;
        this.resources = resources;
    }

    // Constructor parametrizado sin id para inserts
    public Room(String name, int capacity, String resources) {
        this.roomId = 0;
        this.name = name;
        this.capacity = capacity;
        this.resources = resources;
    }

    // Constructor copia
    public Room(Room other) {
        this.roomId = other.roomId;
        this.name = other.name;
        this.capacity = other.capacity;
        this.resources = other.resources;
    }

    // Getters y setters
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    // equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomId == room.roomId &&
                capacity == room.capacity &&
                Objects.equals(name, room.name) &&
                Objects.equals(resources, room.resources);
    }

    // hashCode
    @Override
    public int hashCode() {
        return Objects.hash(roomId, name, capacity, resources);
    }

    // toString
    @Override
    public String toString() {
        return  "Sala: " + name + " (ID: " + roomId + ")\n" +
                "Capacidad: " + capacity + " personas\n" +
                "Recursos: " + resources;
    }
}
