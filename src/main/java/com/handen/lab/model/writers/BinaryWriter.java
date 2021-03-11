package com.handen.lab.model.writers;

import com.handen.lab.data.Employee;

import java.io.File;
import java.util.List;

public class BinaryWriter implements Writer {
    @Override
    public void write(File file, List<Employee> items) {
        //TODO
    }

    @Override
    public String getFileExtension() {
        return "txt";
    }
}
