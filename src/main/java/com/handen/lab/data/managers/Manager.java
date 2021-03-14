package com.handen.lab.data.managers;

import com.handen.lab.data.Employee;

public abstract class Manager extends Employee {
    public Manager(int id, String name, String surname, int salary) {
        super(id, name, surname, salary);
    }

    abstract String doQuarterReport();

    public Manager() {
    }
}
