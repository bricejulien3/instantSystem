package com.bricejulien.instantSystem.models;

import javax.persistence.*;

@Entity
@Table(name = "parking")
public class Parking {

    private String id;
    private String name;
    private int capacity;
    private int availableSpaces;
    // distance en mètres entre l'utilisateur et le parking
    private double distanceToUser;

    // Constructeur avec paramètres
    public Parking(String id, String name, int availableSpaces, int capacity, double distanceToUser) {
        this.id = id;
        this.name = name;
        this.availableSpaces = availableSpaces;
        this.capacity = capacity;
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
