package com.bricejulien.instantSystem.models;

import javax.persistence.*;

@Entity
@Table(name = "parking")
public class Parking {

    private String id;
    private String name;
    private String tariffZone;
    private int availableSpaces;

    // Constructeur avec paramètres
    public Parking(String id, String name, String tariffZone, int availableSpaces) {
        this.id = id;
        this.name = name;
        this.tariffZone = tariffZone;
        this.availableSpaces = availableSpaces;
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

    public String getTariffZone() {
        return tariffZone;
    }

    public void setTariffZone(String tariffZone) {
        this.tariffZone = tariffZone;
    }

    public int getAvailableSpaces() {
        return availableSpaces;
    }

    public void setAvailableSpaces(int availableSpaces) {
        this.availableSpaces = availableSpaces;
    }
}
