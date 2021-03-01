package com.handen.lab.data.designer;

public class UXDesigner extends Designer {
    public UXDesigner(int id, String name, String surname, int salary) {
        super(id, name, surname, salary);
    }

    @Override
    public String getPositionTitle() {
        return "UX Designer";
    }

    @Override
    String doDesign() {
        return "This is UX design";
    }
}
