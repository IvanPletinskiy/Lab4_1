package com.handen.lab.model.writers;

import com.handen.lab.data.Employee;

import java.io.File;
import java.util.List;

public interface IOEmployeesProvider {
    void write(File file, List<Employee> items);
    List<Employee> read(File file);

    String getFileExtension();
}
