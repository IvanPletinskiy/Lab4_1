package com.handen.lab.data.developer;

import com.handen.lab.data.Employee;

abstract class Developer extends Employee {
    public Developer mentor;

    public Developer getMentor() {
        return mentor;
    }

    public void setMentor(Developer mentor) {
        this.mentor = mentor;
    }

    abstract String doDevelopment();
}
