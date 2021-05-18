package com.handen.lab.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import static com.handen.lab.PushNotificationsKt.sendAtTime;
import static com.handen.lab.PushNotificationsKt.sendNow;

public class MainController implements Initializable {

    public TextField title_text_field;
    public TextField message_text_field;
    public DatePicker date_picker;
    public CheckBox now_check_box;
    public Label select_date_label;
    public Label select_time_label;
    public TextField time_text_field;
    public Label error_label;

    public void onSendButtonClicked(ActionEvent actionEvent) {
        boolean sendingNow = now_check_box.isSelected();
        String title = title_text_field.getText();
        String message = message_text_field.getText();
        if (sendingNow) {
            sendNow(title, message);
        } else {
            LocalDate localDate = date_picker.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String timeString = time_text_field.getText();
            long timeMillis = -1;
            try {
                Date timeDate = dateFormat.parse(timeString);
                timeMillis = timeDate.getTime();
            } catch(ParseException exception) {
                exception.printStackTrace();
            }

            long sendMillis = date.getTime() + timeMillis;
            sendAtTime(title, message, sendMillis);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        now_check_box.setSelected(true);
        now_check_box.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                changeDatetimePickerVisibility(!observableValue.getValue());
            }
        });
        changeDatetimePickerVisibility(false);
    }

    private void changeDatetimePickerVisibility(boolean isVisible) {
        select_date_label.setVisible(isVisible);
        date_picker.setVisible(isVisible);
        select_time_label.setVisible(isVisible);
        time_text_field.setVisible(isVisible);
    }

    //    private static final String INITIAL_KEY = "11111111111111111111111111111111111";
//    public TextArea keyTextView;
//    private String decodedPath = "C:\\ti\\lfsr\\decoded.txt";
//    private String encodedPath = "C:\\ti\\lfsr\\encoded.txt";
//    public AnchorPane container;
//    public Label decodedFilePath;
//    public Label encodedFilePath;
//    public TextArea inputTextView;
//    public TextField initialRegisterValue;
//    public TextArea resultTextView;
//    private Stage stage;
//
//    public Label errorLabel;
//
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        initialRegisterValue.setText(INITIAL_KEY);
//        initialRegisterValue.setTextFormatter(new BinaryTextFormatter());
//        updateDecodedPath();
//        updateEncodedPath();
//        errorLabel.setWrapText(true);
//    }
//
//    public void setStage(Stage stage) {
//        this.stage = stage;
//    }
//
//    public void onEncodeClicked(MouseEvent mouseEvent) {
//        boolean isValid = validateKey();
//        if(isValid) {
//            LFSR lfsr = new LFSR(initialRegisterValue.getText());
//            byte[] bytes = readBytesFromPath(decodedPath);
//            byte[] encodedBytes = lfsr.encodeBytes(bytes);
//            StringBuilder bytesString = new StringBuilder();
//            for(int i = 0; i < bytes.length && i < 1000; i++) {
//                bytesString.append(String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0'));
//            }
//            inputTextView.setText(bytesString.toString());
//
//            StringBuilder decodedBytesString = new StringBuilder();
//            for(int i = 0; i < encodedBytes.length && i < 1000; i++) {
//                decodedBytesString.append(String.format("%8s", Integer.toBinaryString(encodedBytes[i] & 0xFF)).replace(' ', '0'));
//            }
//            resultTextView.setText(decodedBytesString.toString());
//            writeBytesToPath(encodedBytes, encodedPath);
//            keyTextView.setText(lfsr.getKey());
//        }
//        else {
//            inputTextView.setText("");
//            resultTextView.setText("");
//        }
//    }
//
//    public void onDecodeClicked(MouseEvent mouseEvent) {
//        boolean isValid = validateKey();
//        if(isValid) {
//            LFSR lfsr = new LFSR(initialRegisterValue.getText());
//            byte[] bytes = readBytesFromPath(encodedPath);
//            byte[] decodedBytes = lfsr.decodeBytes(bytes);
//            StringBuilder bytesString = new StringBuilder();
//            for(int i = 0; i < bytes.length && i < 1000; i++) {
//                bytesString.append(String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0'));
//            }
//            inputTextView.setText(bytesString.toString());
//
//            StringBuilder decodedBytesString = new StringBuilder();
//            for(int i = 0; i < decodedBytes.length && i < 1000; i++) {
//                decodedBytesString.append(String.format("%8s", Integer.toBinaryString(decodedBytes[i] & 0xFF)).replace(' ', '0'));
//            }
//            resultTextView.setText(decodedBytesString.toString());
//            writeBytesToPath(decodedBytes, decodedPath);
//            keyTextView.setText(lfsr.getKey());
//        }
//        else {
//            inputTextView.setText("");
//            resultTextView.setText("");
//        }
//    }
//
//    private boolean validateKey() {
//        String key = initialRegisterValue.getText();
//        boolean isValid = true;
//        if(key.length() != 35) {
//            errorLabel.setText("Ошибка: Длина ключа должна быть 35 символов, сейчас: " + key.length());
//            isValid = false;
//        }
//
//        if(isValid) {
//            errorLabel.setVisible(false);
//            errorLabel.setText("");
//        }
//        else {
//            errorLabel.setVisible(true);
//        }
//
//        return isValid;
//    }
//
//    private byte[] readBytesFromPath(String path) {
//        FileInputStream fileInputStream = null;
//        File file = new File(path);
//        try {
//            fileInputStream = new FileInputStream(file);
//            return fileInputStream.readAllBytes();
//        }
//        catch(IOException e) {
//            e.printStackTrace();
//            return new byte[0];
//        }
//        finally {
//            if(fileInputStream != null) {
//                try {
//                    fileInputStream.close();
//                }
//                catch(IOException exception) {
//                    exception.printStackTrace();
//                }
//            }
//        }
//    }
//
//    private void writeBytesToPath(byte[] bytes, String path) {
//        File file = new File(path);
//        FileOutputStream fileOutputStream = null;
//        try {
//            fileOutputStream = new FileOutputStream(file);
//            fileOutputStream.write(bytes);
//        }
//        catch(IOException exception) {
//            exception.printStackTrace();
//        }
//        finally {
//            if(fileOutputStream != null) {
//                try {
//                    fileOutputStream.close();
//                }
//                catch(IOException exception) {
//                    exception.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public void onOpenDecodedFile(ActionEvent actionEvent) {
//        File file = chooseFile("Open decoded file");
//        if(file != null) {
//            decodedPath = file.getAbsolutePath();
//            updateDecodedPath();
//        }
//    }
//
//    public void onOpenEncodedFile(ActionEvent actionEvent) {
//        File file = chooseFile("Open encoded file");
//        if(file != null) {
//            encodedPath = file.getAbsolutePath();
//            updateEncodedPath();
//        }
//    }
//
//    private File chooseFile(String title) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle(title);
//        String userDirectoryString = System.getProperty("user.home");
//        File userDirectory = new File(userDirectoryString);
//        if(!userDirectory.canRead()) {
//            userDirectory = new File("c:/");
//        }
//        fileChooser.setInitialDirectory(userDirectory);
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*", "*.*"));
//        return fileChooser.showOpenDialog(stage);
//    }
//
//    private void updateDecodedPath() {
//        decodedFilePath.setText("Расшифрованный файл:" + decodedPath);
//    }
//
//    private void updateEncodedPath() {
//        encodedFilePath.setText("Зашифрованный файл:" + encodedPath);
//    }
}