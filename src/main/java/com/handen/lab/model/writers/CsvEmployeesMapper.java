package com.handen.lab.model.writers;

import com.handen.lab.data.Employee;
import com.handen.lab.data.designer.UIDesigner;
import com.handen.lab.data.designer.UXDesigner;
import com.handen.lab.data.developer.BackendDeveloper;
import com.handen.lab.data.developer.Developer;
import com.handen.lab.data.developer.FrontendDeveloper;
import com.handen.lab.data.developer.MobileDeveloper;
import com.handen.lab.data.managers.DepartmentManager;
import com.handen.lab.data.managers.ProjectManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javafx.util.Pair;

public class CsvEmployeesMapper implements EmployeesMapper {

    @Override
    public List<Employee> read(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<Pair<Employee, Integer>> employeesHolders = reader.lines().map(string -> {
                String[] values = string.split(";");
                Employee employee = createEmployeeFromPosition(values[0]);
                employee.positionTitle = values[0];
                employee.id = Integer.parseInt(values[1]);
                employee.name = values[2];
                employee.surname = values[3];
                employee.salary = Integer.parseInt(values[4]);

                int mentorId = -1;

                if(employee.positionTitle.contains("Developer")) {
                    mentorId = Integer.parseInt(values[5]);
                }

                return new Pair<>(employee, mentorId);
            }).collect(Collectors.toList());

            employeesHolders.forEach(pair -> {
                if(pair.getValue() >= 0) {
                    final Developer[] mentor = new Developer[1];
                    employeesHolders.forEach(it -> {
                        if(it.getKey().getId() == pair.getValue()) {
                            mentor[0] = (Developer) it.getKey();
                        }
                    });
                    ((Developer) pair.getKey()).mentor = mentor[0];
                }
            });

            return employeesHolders.stream().map(Pair::getKey).collect(Collectors.toList());
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
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

    Employee createEmployeeFromPosition(String position) {
        switch(position) {
            case "UI Designer": {
                return new UIDesigner();
            }
            case "UX Designer": {
                return new UXDesigner();
            }
            case "Backend Developer": {
                return new BackendDeveloper();
            }
            case "Frontend Developer": {
                return new FrontendDeveloper();
            }
            case "Mobile Developer": {
                return new MobileDeveloper();
            }
            case "Department Manager": {
                return new DepartmentManager();
            }
            case "Project Manager": {
                return new ProjectManager();
            }
        }
        throw new IllegalArgumentException();
    }
}