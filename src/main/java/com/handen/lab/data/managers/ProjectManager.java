package com.handen.lab.data.managers;

public class ProjectManager extends Manager {
    @Override
    String doQuarterReport() {
        return "PM quarter report";
    }

    @Override
    public String getPositionTitle() {
        return "Project Manager";
    }
}
