package com.handen.lab.data.developer;

import com.handen.lab.data.Employee;

public abstract class Developer extends Employee {
    public Developer(int id, String name, String surname, int salary) {
        super(id, name, surname, salary);
    }

    public Developer mentor;

    public Developer getMentor() {
        return mentor;
    }

    public void setMentor(Developer mentor) {
        this.mentor = mentor;
    }

    abstract String doDevelopment();

    @Override
    public String toCsv() {
        int mentorId = -1;
        if(mentor != null) {
            mentorId = mentor.id;
        }
        return super.toCsv() + ";" + mentorId;
    }
}
