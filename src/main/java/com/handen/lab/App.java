package com.handen.lab;

import com.handen.lab.controller.MainController;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("main_layout.fxml"));
        Scene scene = new Scene(loader.load());
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