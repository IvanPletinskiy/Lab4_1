package com.handen.lab.model.writers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.handen.lab.data.Employee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class XmlEmployeesMapper implements EmployeesMapper {
    @Override
    public void write(File file, List<Employee> items) {
        XmlMapper mapper = new XmlMapper();
        try {
            mapper.writeValue(file, items.toArray());
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
            Employee[] employeesArray = mapper.readerForArrayOf(Employee.class).readValue(xmlString);
            return Arrays.asList(employeesArray);
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
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}
