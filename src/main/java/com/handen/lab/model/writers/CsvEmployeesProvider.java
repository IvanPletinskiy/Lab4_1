package com.handen.lab.model.writers;

import com.handen.lab.data.Employee;

import java.io.File;
import java.util.List;

public class CsvEmployeesProvider implements IOEmployeesProvider {

    @Override
    public List<Employee> read(File file) {
        return null;
    }

    @Override
    public void write(File file, List<Employee> items) {

    }

    @Override
    public String getFileExtension() {
        return "cvs";
    }
}
