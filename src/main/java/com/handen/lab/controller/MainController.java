package com.handen.lab.controller;

import com.handen.lab.ColumnMethod;
import com.handen.lab.DecimationMethod;
import com.handen.lab.Method;
import com.handen.lab.VijinerMethod;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController implements Initializable {

    public AnchorPane container;
    public ComboBox<Method> combobox;
    public Label decodedFilePath;
    public Label encodedFilePath;
    public TextField inputText;
    public TextField keyText;
    public Button encodeButton;
    public Button decodeButton;
    private Stage stage;

    public Label errorLabel;
    private ObservableList<Method> methodsList = FXCollections.observableArrayList(new ColumnMethod(), new DecimationMethod(), new VijinerMethod());

    private String encodeFilePath = "C:\\ti\\";

    public void initialize(URL url, ResourceBundle resourceBundle) {
        combobox.setItems(methodsList);

    }

    private void onMethodSelected(Method method) {
        inputText.clear();
        keyText.clear();
        errorLabel.setVisible(false);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}