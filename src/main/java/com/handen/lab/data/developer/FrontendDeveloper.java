package com.handen.lab.data.developer;

public class FrontendDeveloper extends Developer {
    public FrontendDeveloper(int id, String name, String surname, int salary) {
        super(id, name, surname, salary);
    }

    public FrontendDeveloper() {
    }

    @Override
    public String getPositionTitle() {
        return "Frontend Developer";
    }

    @Override
    String doDevelopment() {
        return "This is frontend development";
    }
}
