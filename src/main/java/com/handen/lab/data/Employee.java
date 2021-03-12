package com.handen.lab.data;

import java.io.Serializable;

public abstract class Employee implements Serializable {
    public int id;
    public String name, surname;
    public int salary;
    public String positionTile;

    public Employee(int id, String name, String surname, int salary) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
    }

    public Employee() {
        this.positionTile = getPositionTitle();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getSalary() {
        return salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public abstract String getPositionTitle();
}
