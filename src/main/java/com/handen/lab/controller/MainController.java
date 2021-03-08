package com.handen.lab.controller;

import com.handen.lab.ColumnMethod;
import com.handen.lab.DecimationMethod;
import com.handen.lab.Method;
import com.handen.lab.Result;
import com.handen.lab.VijinerMethod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController implements Initializable {
    public AnchorPane container;
    public ComboBox<Method> combobox;
    public Label decodedFilePath;
    public Label encodedFilePath;
    public TextArea inputTextView;
    public TextField keyText;
    public Button encodeButton;
    public Button decodeButton;
    public TextArea resultTextView;
    private Stage stage;

    public Label errorLabel;
    private final ObservableList<Method> methodsList = FXCollections.observableArrayList(new ColumnMethod(), new DecimationMethod(), new VijinerMethod());

    public void initialize(URL url, ResourceBundle resourceBundle) {
        combobox.setOnAction(actionEvent -> {
            onMethodSelected(combobox.getValue());
        });
        combobox.setItems(methodsList);
        combobox.setValue(methodsList.get(0));

        onMethodSelected(methodsList.get(0));
        errorLabel.setWrapText(true);
    }

    private void onMethodSelected(Method method) {
        String filePath = combobox.getValue().getDecodedPath();
        String inputText = readTextFromPath(filePath);
        inputTextView.setText(inputText);
        decodedFilePath.setText("Дешифрованный файл: " + combobox.getValue().getDecodedPath());
        encodedFilePath.setText("Зашифрованный файл: " + combobox.getValue().getEncodedPath());
        keyText.clear();
        resultTextView.clear();
        errorLabel.setVisible(false);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void onDecodeClicked(MouseEvent mouseEvent) {
        String filePath = combobox.getValue().getEncodedPath();
        String inputText = readTextFromPath(filePath);
        inputTextView.setText(inputText);
        boolean keyValid = validateKey();
        if(keyValid) {
            Result<String> result = combobox.getValue().decode(inputText, keyText.getText());
            if(result instanceof Result.Success) {
                String text = ((Result.Success<String>) result).getValue();
                resultTextView.setText(text);
                writeTextToPath(text, combobox.getValue().getDecodedPath());
            }
            else {
                errorLabel.setVisible(true);
                errorLabel.setText(((Result.Error) result).getMessage());
            }
        }
    }

    public void onEncodeClicked(MouseEvent mouseEvent) {
        String filePath = combobox.getValue().getDecodedPath();
        String inputText = readTextFromPath(filePath);
        inputTextView.setText(inputText);
        boolean keyValid = validateKey();
        if(keyValid) {
            Result<String> result = combobox.getValue().encode(inputText, keyText.getText());
            if(result instanceof Result.Success) {
                String text = ((Result.Success<String>) result).getValue();
                resultTextView.setText(text);
                writeTextToPath(text, combobox.getValue().getEncodedPath());

            }
            else {
                errorLabel.setVisible(true);
                errorLabel.setText(((Result.Error) result).getMessage());
            }
        }
    }

    private boolean validateKey() {
        String key = keyText.getText();
        boolean isValid = true;
        if(key.isBlank()) {
            errorLabel.setText("Ошибка: Ключ не может быть пустым");
            isValid = false;
        }
        if(combobox.getValue() instanceof VijinerMethod) {
            for(char c : key.toCharArray()) {
                if(c != 'Ё' && c != 'ё' && ((c < 'а' || c > 'я') && (c < 'А' || c > 'Я'))) {
                    isValid = false;
                    errorLabel.setText("Ошибка: Ключ должен содержать только символы русского алфавита");
                }
            }
        }
        if(combobox.getValue() instanceof DecimationMethod) {
            for(char c : key.toCharArray()) {
                if(!Character.isDigit(c)) {
                    isValid = false;
                    errorLabel.setText("Ошибка: Ключ должен содержать только цифры");
                }
            }
        }

        if(combobox.getValue() instanceof ColumnMethod) {
            for(char c : key.toCharArray()) {
                if((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) {
                    isValid = false;
                    errorLabel.setText("Ошибка: Ключ должен содержать только символы английского алфавита");
                }
            }
        }

        if(isValid) {
            errorLabel.setVisible(false);
            errorLabel.setText("");
        }
        else {
            errorLabel.setVisible(true);
        }

        return isValid;
    }

    public String readTextFromPath(String path) {
        File file = new File(path);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return bufferedReader.lines().collect(Collectors.joining());
        }
        catch(Exception e) {
            e.printStackTrace();
            return "";
        }
        finally {
            try {
                bufferedReader.close();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeTextToPath(String text, String path) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(path, false);
            fileWriter.write(text);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                fileWriter.close();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}