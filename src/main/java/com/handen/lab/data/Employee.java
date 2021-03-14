package com.handen.lab.data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.handen.lab.data.designer.UIDesigner;
import com.handen.lab.data.designer.UXDesigner;
import com.handen.lab.data.developer.BackendDeveloper;
import com.handen.lab.data.developer.FrontendDeveloper;
import com.handen.lab.data.developer.MobileDeveloper;
import com.handen.lab.data.managers.DepartmentManager;
import com.handen.lab.data.managers.ProjectManager;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UIDesigner.class),
        @JsonSubTypes.Type(value = UXDesigner.class),
        @JsonSubTypes.Type(value = BackendDeveloper.class),
        @JsonSubTypes.Type(value = FrontendDeveloper.class),
        @JsonSubTypes.Type(value = MobileDeveloper.class),
        @JsonSubTypes.Type(value = DepartmentManager.class),
        @JsonSubTypes.Type(value = ProjectManager.class),
}
)
public abstract class Employee implements Serializable {
    public int id;
    public String name, surname;
    public int salary;
    public String positionTitle;

    public Employee(int id, String name, String surname, int salary) {
        this.positionTitle = getPositionTitle();
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
    }

    public Employee() {
        this.positionTitle = getPositionTitle();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getSalary() {
        return salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public abstract String getPositionTitle();

    public String toCsv() {
        return this.positionTitle + ";" + this.id + ";" + this.name + ";" + this.surname + ";" + this.salary;
    }
}