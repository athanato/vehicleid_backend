package com.project.vehicleid.Model;


import javax.persistence.*;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "model")
    private String model;

    @Column(name = "description")
    private String description;

    @Column(name = "registered")
    private boolean registered;

    public Vehicle() {

    }

    public Vehicle(String model, String description, boolean registered) {
        this.model = model;
        this.description = description;
        this.registered = registered;
    }

    public long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean isRegistered) {
        this.registered = isRegistered;
    }

    @Override
    public String toString() {
        return "Vehicle [id=" + id + ", model=" + model + ", desc=" + description + ", registered=" + registered + "]";
    }

}