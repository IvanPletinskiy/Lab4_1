package com.handen.lab.model;

import com.handen.lab.data.Employee;
import com.handen.lab.data.developer.MobileDeveloper;
import com.handen.lab.model.writers.EmployeesMapper;

import java.io.File;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ActualRepository implements Repository {
    ObservableList<Employee> items = FXCollections.observableArrayList(new MobileDeveloper(0, "Ivan", "Pletinskiy", 100500));

    public void saveToFile(EmployeesMapper mapper, File file) {
        if(file != null) {
            mapper.write(file, getItems());
        }
    }

    public void loadFromFile(EmployeesMapper mapper, File file) {
        if(file != null) {
            List<Employee> employeeList = mapper.read(file);
            getItems().setAll(employeeList);
        }
    }

    @Override
    public ObservableList<Employee> getItems() {
        return items;
    }
}

