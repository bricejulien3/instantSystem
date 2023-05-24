package com.bricejulien.instantSystem.models;

import javax.persistence.*;

@Entity
@Table(name = "parking")
public class Parking {

    private String id;
    private String name;
    private int capacity;
    private int availableSpaces;
    private double distanceToUser; // distance en mètres

    // Constructeur avec paramètres
    public Parking(String id, String name, int availableSpaces, double distanceToUser) {
        this.id = id;
        this.name = name;
        this.availableSpaces = availableSpaces;
        this.distanceToUser = distanceToUser;
    }

    // Getters et setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getAvailableSpaces() {
        return availableSpaces;
    }

    public void setAvailableSpaces(int availableSpaces) {
        this.availableSpaces = availableSpaces;
    }

    public double getDistanceToUser() {
        return distanceToUser;
    }

    public void setDistanceToUser(double distanceToUser) {
        this.distanceToUser = distanceToUser;
    }
}
