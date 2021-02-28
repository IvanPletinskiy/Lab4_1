package com.handen.lab.data.managers;

public class DepartmentManager extends Manager {
    @Override
    public String getPositionTitle() {
        return "Department Manager";
    }

    @Override
    String doQuarterReport() {
        return "This is department manager quarter report";
    }
}
