package com.handen.lab.controller;

import com.handen.lab.ColumnMethod;
import com.handen.lab.DecimationMethod;
import com.handen.lab.LFSR;
import com.handen.lab.Method;
import com.handen.lab.Result;
import com.handen.lab.VijinerMethod;
import com.handen.lab.utils.BinaryTextFormatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController implements Initializable {
    private static final String INITIAL_KEY = "11111111111111111111111111111111111";
    private String decodedPath = "C:\\ti\\lfsr\\decoded.txt";
    private String encodedPath = "C:\\ti\\lfsr\\encoded.txt";
    public AnchorPane container;
    public Label decodedFilePath;
    public Label encodedFilePath;
    public TextArea inputTextView;
    public TextField keyText;
    public TextArea resultTextView;
    private Stage stage;

    public Label errorLabel;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        keyText.setText(INITIAL_KEY);
        keyText.setTextFormatter(new BinaryTextFormatter());
        errorLabel.setWrapText(true);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void onEncodeClicked(MouseEvent mouseEvent) {
        boolean isValid = validateKey();
        if(isValid) {
            String text = readTextFromPath(decodedPath);
            String encoded = new LFSR(keyText.getText()).encodeString(text);
            writeTextToPath(encoded, encodedPath);
        }
    }

    public void onDecodeClicked(MouseEvent mouseEvent) {
        boolean isValid = validateKey();
        if(isValid) {
            String text = readTextFromPath(encodedPath);
            String decoded = new LFSR(keyText.getText()).decodeString(text);
            writeTextToPath(decoded, decodedPath);
        }
    }

    private boolean validateKey() {
        String key = keyText.getText();
        boolean isValid = true;
        if(key.length() != 35) {
            errorLabel.setText("Ошибка: Длина ключа должна быть 35 символов");
            isValid = false;
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