package com.handen.lab.data.managers;

public class ProjectManager extends Manager {
    public ProjectManager(int id, String name, String surname, int salary) {
        super(id, name, surname, salary);
    }

    @Override
    String doQuarterReport() {
        return "PM quarter report";
    }

    @Override
    public String getPositionTitle() {
        return "Project Manager";
    }
}
