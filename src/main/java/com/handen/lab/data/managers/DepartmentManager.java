package com.handen.lab.data.managers;

public class DepartmentManager extends Manager {
    public DepartmentManager(int id, String name, String surname, int salary) {
        super(id, name, surname, salary);
    }

    public DepartmentManager() {
    }

    @Override
    public String getPositionTitle() {
        return "Department Manager";
    }

    @Override
    String doQuarterReport() {
        return "This is department manager quarter report";
    }
}
