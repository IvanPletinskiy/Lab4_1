package com.handen.lab.data.designer;

import com.handen.lab.data.Employee;

abstract class Designer extends Employee {
    public Designer(int id, String name, String surname, int salary) {
        super(id, name, surname, salary);
    }

    public Designer() {
    }

    abstract String doDesign();
}
