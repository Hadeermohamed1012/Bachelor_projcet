package com.example.myapplication.Model;

public class AvailableRooms {
    public  String Id, Capacity ,Location;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCapacity() {
        return Capacity;
    }

    public void setCapacity(String capacity) {
        Capacity = capacity;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public  AvailableRooms(String id, String capacity, String location) {
        this.Id = id;
        this.Capacity = capacity;
       this. Location = location;
    }
}
