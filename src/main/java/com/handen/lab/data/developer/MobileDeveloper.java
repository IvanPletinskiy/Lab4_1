package com.handen.lab.data.developer;

public class MobileDeveloper extends Developer {
    public MobileDeveloper(int id, String name, String surname, int salary) {
        super(id, name, surname, salary);
    }

    public MobileDeveloper() {

    }

    @Override
    public String getPositionTitle() {
        return "Mobile Developer";
    }

    @Override
    String doDevelopment() {
        return "This is mobile development";
    }
}