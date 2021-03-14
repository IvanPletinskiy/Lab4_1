package com.handen.lab.model.writers;

import com.handen.lab.data.Employee;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CsvEmployeesMapper implements EmployeesMapper {

    @Override
    public List<Employee> read(File file) {
        return Collections.emptyList();
    }

    @Override
    public void write(File file, List<Employee> items) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(Employee employee : items) {
                writer.write(employee.toCsv() + "\n");
            }
            writer.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFileExtension() {
        return "csv";
    }
}