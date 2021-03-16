package com.handen.xmlToJson;

import org.json.XML;

import java.util.Arrays;

class Main {
    public static void main(String[] args) {
        String input = "<EmployeesList><employees><employees _type=\"MobileDeveloper\"><id>0</id><name>Ivan</name><surname>Pletinskiy</surname><salary>100500</salary><positionTitle>Mobile Developer</positionTitle><mentorId>0</mentorId><mentor/></employees></employees></EmployeesList>";
        String isEncodingString = "true";
        boolean isEncoding = Boolean.parseBoolean(isEncodingString);
        String output = "";
        if(isEncoding) {
            output = XML.toJSONObject(input).toString();
        } else {
            output = XML.toString(input);
        }

        System.out.println(Arrays.toString(args));
        System.out.println("Hello from jar");
    }
}

//java -cp A.jar com.handen.xmlToJson.Main