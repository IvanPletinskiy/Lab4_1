package com.handen.plugin;

import org.json.JSONObject;
import org.json.XML;

class Main {
    public static void main(String[] args) {
        String mode = args[0];
        String input = args[1];
//        String input = "{\"EmployeesList\":{\"employees\":{\"employees\":{\"mentor\":\"\",\"positionTitle\":\"Mobile Developer\",\"surname\":\"Pletinskiy\",\"_type\":\"MobileDeveloper\",\"name\":\"Ivan\",\"id\":0,\"salary\":100500,\"mentorId\":0}}}}";

        String output = "";

        if(mode.equals("encode")) {
            output = XML.toJSONObject(input).toString();
        } else {
            JSONObject jsonObject = new JSONObject(input);
            output = XML.toString(jsonObject);
        }

        System.out.print(output);
    }
}