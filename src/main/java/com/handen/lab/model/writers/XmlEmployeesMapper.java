package com.handen.lab.model.writers;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.handen.lab.data.Employee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class XmlEmployeesMapper implements EmployeesMapper {

    private String pluginPath;

    public XmlEmployeesMapper(String pluginPath) {
        this.pluginPath = pluginPath;
    }

    @Override
    public void write(File file, List<Employee> items) {
        XmlConverter xmlConverter = new XmlAdapter(items);
        try {
            String xml = xmlConverter.getXml();
            if(pluginEnabled()) {
                xml = applyPlugin(xml, true);
            }
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(xml);
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
            String input = inputStreamToString(new FileInputStream(file));
            if(pluginEnabled()) {
                input = applyPlugin(input, false);
            }
            EmployeesList employeesList = mapper.readValue(input, EmployeesList.class);
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

    private boolean pluginEnabled() {
        return !pluginPath.isEmpty();
    }

    String applyPlugin(String input, boolean isEncoding) {
        String modeString;
        if(isEncoding) {
            modeString = "encode";
        }
        else {
            modeString = "decode";
        }
        try {
            Process proc = Runtime.getRuntime().exec(new String[]{"java", "-cp", pluginPath, "com.handen.plugin.Main", modeString, input});

            InputStream in = proc.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String output = bufferedReader.lines().collect(Collectors.joining());
            return output;
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return "Plugin error";
    }
}

class XmlAdapter implements XmlConverter {
    private final EmployeesList list;

    public XmlAdapter(List<Employee> employees) {
        this.list = new EmployeesList(employees);
    }

    public EmployeesList getList() {
        return list;
    }

    @Override
    public String getXml() {
        XmlMapper mapper = new XmlMapper();
        String mappedString = "";
        try {
            mappedString = mapper.writeValueAsString(list);
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }
        return mappedString;
    }
}

interface XmlConverter {
    String getXml();
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
