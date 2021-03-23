package com.handen.lab.controller;

import com.handen.lab.LFSR;
import com.handen.lab.utils.BinaryTextFormatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController implements Initializable {
    private static final String INITIAL_KEY = "11111111111111111111111111111111111";
    public CheckBox checkbox;
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
        updateDecodedPath();
        updateEncodedPath();
        errorLabel.setWrapText(true);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void onEncodeClicked(MouseEvent mouseEvent) {
        boolean isValid = validateKey();
        if(isValid) {
            if(checkbox.isSelected()) {
                String text = readTextFromPath(decodedPath);
                String encoded = new LFSR(keyText.getText()).encodeString(text);
                inputTextView.setText(text);
                resultTextView.setText(encoded);
                writeTextToPath(encoded, encodedPath);
            }
            else {
                byte[] bytes = readBytesFromPath(decodedPath);
                byte[] encodedBytes = new LFSR(keyText.getText()).encodeBytes(bytes);
                inputTextView.setText("");
                resultTextView.setText("");
                writeBytesToPath(encodedBytes, encodedPath);
            }
        }
        else {
            inputTextView.setText("");
            resultTextView.setText("");
        }
    }

    public void onDecodeClicked(MouseEvent mouseEvent) {
        boolean isValid = validateKey();
        if(isValid) {
            if(checkbox.isSelected()) {
                String text = readTextFromPath(encodedPath);
                String decoded = new LFSR(keyText.getText()).decodeString(text);
                inputTextView.setText(text);
                resultTextView.setText(decoded);
                writeTextToPath(decoded, decodedPath);
            }
            else {
                byte[] bytes = readBytesFromPath(encodedPath);
                byte[] decodedBytes = new LFSR(keyText.getText()).decodeBytes(bytes);
                inputTextView.setText("");
                resultTextView.setText("");
                writeBytesToPath(decodedBytes, decodedPath);
            }
        }
        else {
            inputTextView.setText("");
            resultTextView.setText("");
        }
    }

    private boolean validateKey() {
        String key = keyText.getText();
        boolean isValid = true;
        if(key.length() != 35) {
            errorLabel.setText("Ошибка: Длина ключа должна быть 35 символов, сейчас: " + key.length());
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

    private byte[] readBytesFromPath(String path) {
        FileInputStream fileInputStream = null;
        File file = new File(path);
        try {
            fileInputStream = new FileInputStream(file);
            return fileInputStream.readAllBytes();
        }
        catch(IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
        finally {
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                }
                catch(IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    private String readTextFromPath(String path) {
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

    private void writeBytesToPath(byte[] bytes, String path) {
        File file = new File(path);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
        finally {
            if(fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                }
                catch(IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    private void writeTextToPath(String text, String path) {
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
                if(fileWriter != null) {
                    fileWriter.close();
                }
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onOpenDecodedFile(ActionEvent actionEvent) {
        File file = chooseFile("Open decoded file");
        if(file != null) {
            decodedPath = file.getAbsolutePath();
            updateDecodedPath();
        }
    }

    public void onOpenEncodedFile(ActionEvent actionEvent) {
        File file = chooseFile("Open encoded file");
        if(file != null) {
            encodedPath = file.getAbsolutePath();
            updateEncodedPath();
        }
    }

    private File chooseFile(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        String userDirectoryString = System.getProperty("user.home");
        File userDirectory = new File(userDirectoryString);
        if(!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }
        fileChooser.setInitialDirectory(userDirectory);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*", "*.*"));
        return fileChooser.showOpenDialog(stage);
    }

    private void updateDecodedPath() {
        decodedFilePath.setText("Расшифрованный файл:" + decodedPath);
    }

    private void updateEncodedPath() {
        encodedFilePath.setText("Зашифрованный файл:" + encodedPath);
    }
}