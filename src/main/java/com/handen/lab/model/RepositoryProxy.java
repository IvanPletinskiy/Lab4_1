package com.handen.lab.model;

import com.handen.lab.data.Employee;
import com.handen.lab.data.developer.Developer;
import com.handen.lab.data.developer.MobileDeveloper;
import com.handen.lab.model.writers.EmployeesMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RepositoryProxy implements Repository {
    private final ActualRepository mActualRepository;
    private static RepositoryProxy instance = null;
    private static int id = 1;

    private RepositoryProxy() {
        mActualRepository = new ActualRepository();
    }

    public Developer getMentorBySurname(String surname) {
        for(Employee employee : getItems()) {
            if(employee.getSurname().equals(surname)) {
                return (Developer) employee;
            }
        }
        return null;
    }

    public static RepositoryProxy getInstance() {
        if(instance == null) {
            instance = new RepositoryProxy();
        }
        return instance;
    }

    public ObservableList<Employee> getItems() {
        return mActualRepository.items;
    }

    public static int getNewId() {
        id++;
        return id;
    }

    public void saveEmployee(int index, Employee employee) {
        getItems().set(index, employee);
    }

    @Override
    public void saveToFile(EmployeesMapper mapper, File file) {
        mActualRepository.saveToFile(mapper, file);
    }

    @Override
    public void loadFromFile(EmployeesMapper mapper, File file) {
        mActualRepository.loadFromFile(mapper, file);
    }
}
