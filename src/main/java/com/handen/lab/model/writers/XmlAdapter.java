package com.handen.lab.model.writers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.handen.lab.data.Employee;

import java.util.List;

class XmlAdapter implements XmlConverter {
    private final EmployeesList list;

    public XmlAdapter(List<Employee> employees) {
        this.list = new EmployeesList(employees);
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
