package com.handen.lab.data.designer;

public class UIDesigner extends Designer {
    public UIDesigner(int id, String name, String surname, int salary) {
        super(id, name, surname, salary);
    }

    public UIDesigner() {
    }

    @Override
    public String getPositionTitle() {
        return "UI Designer";
    }

    @Override
    String doDesign() {
        return "This is UI design";
    }
}
