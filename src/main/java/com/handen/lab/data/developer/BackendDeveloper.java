package com.handen.lab.data.developer;

public class BackendDeveloper extends Developer {
    public BackendDeveloper(int id, String name, String surname, int salary) {
        super(id, name, surname, salary);
    }

    @Override
    public String getPositionTitle() {
        return "Backend Developer";
    }

    @Override
    String doDevelopment() {
        return "This is backend development";
    }
}
