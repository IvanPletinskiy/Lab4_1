package com.handen.lab.model.writers;

import com.handen.lab.data.Employee;

import java.io.File;
import java.util.List;

public class XmlIOEmployeesProvider implements IOEmployeesProvider {
    @Override
    public void write(File file, List<Employee> items) {

    }

    @Override
    public String getFileExtension() {
        return "xml";
    }

    @Override
    public List<Employee> read(File file) {
        return null;
    }
}
