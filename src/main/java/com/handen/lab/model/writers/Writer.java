package com.handen.lab.model.writers;

import com.handen.lab.data.Employee;

import java.io.File;
import java.util.List;

public interface Writer {
    void write(File file, List<Employee> items);

    String getFileExtension();
}
