package com.handen.lab.model.writers;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.handen.lab.data.Employee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class XmlEmployeesMapper implements EmployeesMapper {
    @Override
    public void write(File file, List<Employee> items) {
        XmlMapper mapper = new XmlMapper();
        EmployeesList list = new EmployeesList(items);

        try {
            mapper.writeValue(file, list);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFileExtension() {
        return "xml";
    }

    @Override
    public List<Employee> read(File file) {
        XmlMapper mapper = new XmlMapper();
        try {
            String xmlString = inputStreamToString(new FileInputStream(file));
            EmployeesList employeesList = mapper.readValue(xmlString, EmployeesList.class);
            return employeesList.getEmployees();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}

class EmployeesList implements Serializable {

    @JsonUnwrapped(enabled = false)
    List<Employee> employees;

    EmployeesList(List<Employee> employees) {
        this.employees = employees;
    }

    //Don't delete, default constructor is required for serialization.
    EmployeesList() {

    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
