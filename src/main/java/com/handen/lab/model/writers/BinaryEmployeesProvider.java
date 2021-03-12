package com.handen.lab.model.writers;

import com.handen.lab.data.Employee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BinaryEmployeesProvider implements IOEmployeesProvider {
    @Override
    public void write(File file, List<Employee> items) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(items.toArray());
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> read(File file) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            Employee[] employeesArray = (Employee[]) objectInputStream.readObject();
            List<Employee> employees = Arrays.asList(employeesArray);
            return employees;
        }
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public String getFileExtension() {
        return "txt";
    }
}
