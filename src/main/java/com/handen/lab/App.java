package com.handen.lab;

import com.handen.lab.controller.MainController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
//        String output = "-1";
//        try {
//            Process proc = Runtime.getRuntime().exec(new String[]{"java", "-cp", "C:\\oop\\XmlTagsToAttributes.jar", "com.handen.plugin.Main", "encode", "<EmployeesList><employees><employees _type=\"MobileDeveloper\"><id>0</id><name>Ivan</name><surname>Pletinskiy</surname><salary>100500</salary><positionTitle>Mobile Developer</positionTitle><mentorId>0</mentorId><mentor/></employees></employees></EmployeesList>"});
//
//            InputStream in = proc.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
//            output = bufferedReader.lines().collect(Collectors.joining());
//        }
//        catch(IOException e) {
//            e.printStackTrace();
//        }
        FXMLLoader loader = new FXMLLoader(App.class.getResource("main_layout.fxml"));
        Scene scene = new Scene(loader.load(), 800, 400);
        MainController controller = loader.getController();
        controller.setStage(stage);
        stage.setTitle("Ivan Pletinski 951008");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

//{"EmployeesList":{"employees":{"employees":{"mentor":"","positionTitle":"Mobile Developer","surname":"Pletinskiy","_type":"MobileDeveloper","name":"Ivan","id":0,"salary":100500,"mentorId":0}}}}

//<EmployeesList><employees><employees><mentor/><positionTitle>Mobile Developer</positionTitle><surname>Pletinskiy</surname><_type>MobileDeveloper</_type><name>Ivan</name><id>0</id><salary>100500</salary><mentorId>0</mentorId></employees></employees></EmployeesList>