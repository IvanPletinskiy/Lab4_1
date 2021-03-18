package com.handen.lab.model;

import com.handen.lab.data.Employee;
import com.handen.lab.model.writers.EmployeesMapper;

import java.io.File;

import javafx.collections.ObservableList;

public interface Repository {
    void saveToFile(EmployeesMapper mapper, File file);

    void loadFromFile(EmployeesMapper mapper, File file);

    ObservableList<Employee> getItems();
}
