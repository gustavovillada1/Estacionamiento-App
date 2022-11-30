package com.gustavovillada.estacionamiento.model;

import java.io.Serializable;

public class Zona implements Serializable {

    private String name;
    private int capacity;
    private int ocupation;
    private String instructions;

    public Zona(String name, int capacity, int ocupation, String instructions) {
        this.name = name;
        this.capacity = capacity;
        this.ocupation = ocupation;
        this.instructions = instructions;
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

    public int getOcupation() {
        return ocupation;
    }

    public void setOcupation(int ocupation) {
        this.ocupation = ocupation;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

}
