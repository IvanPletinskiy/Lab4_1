package com.handen.lab.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class MainController {
    // Подпись
    private Long pNumber;
    private Long qNumber;
    private Long rNumber;
    private Long eulerFunction;
    private Long dNumber;
    private Long eNumber;
    private Long signHash;
    private Long sign;

    //Проверка подписи
    private Long signCheckRNumber;
    private Long signCheckENumber;
    private Long signCheckHash;
    private Long signCheckSign;

    @FXML
    private TextField InputPTextField;

    @FXML
    private TextField InputQTextField;

    @FXML
    private TextField RTextField;

    @FXML
    private TextField EulerFunctionTextField;

    @FXML
    private TextField InputDTextField;

    @FXML
    private TextField ETextField;

    @FXML
    private TextArea SignCheckHashTextArea;

    @FXML
    private TextArea SignTextArea;

    @FXML
    private TextField InputSignCheckRTextField;

    @FXML
    private TextField InputSignCheckETextField;

    @FXML
    private TextArea HashTextArea;

    public void showErrorMessage(String errorText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("");
        alert.setContentText(errorText);
        alert.showAndWait();
    }

    //Input checkers
    public boolean isPrimeNumber(long num) {
        long temp;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            temp = num % i;
            if (temp == 0) {
                return false;
            }
        }
        return true;
    }

    public long findENumber(long a, long b) {
        long d0 = a;
        long d1 = b;
        long x0 = 1;
        long x1 = 0;
        long y0 = 0;
        long y1 = 1;
        while (d1 > 1) {
            long q = d0 / d1;
            long d2 = d0 % d1;
            long x2 = x0 - q * x1;
            long y2 = y0 - q * y1;
            d0 = d1;
            d1 = d2;
            x0 = x1;
            x1 = x2;
            y0 = y1;
            y1 = y2;
        }
        return y1;
    }

    public long gcd(long e, long z) {
        if (e == 0) {
            return z;
        } else {
            return gcd(z % e, e);
        }
    }

    boolean isCorrectPQ(String p) {
        long temp;
        try {
            temp = Long.parseLong(p);
        } catch (NumberFormatException e) {
            return false;
        }
        return isPrimeNumber(temp);
    }

    boolean isCorrectLongNumber(String d) {
        long temp;
        try {
            temp = Long.parseLong(d);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    // Input values
    @FXML
    public void inputPQ(ActionEvent event) {
        if (InputQTextField.getText().isEmpty() || InputPTextField.getText().isEmpty()) {
            showErrorMessage("Пожалуйста, заполните поля p и q");
        } else {
            if (isCorrectPQ(InputPTextField.getText()) && isCorrectPQ(InputQTextField.getText())) {
                RTextField.clear();
                EulerFunctionTextField.clear();
                pNumber = Long.parseLong(InputPTextField.getText());
                qNumber = Long.parseLong(InputQTextField.getText());
                rNumber = pNumber * qNumber;
                RTextField.setText(String.valueOf(rNumber));

                eulerFunction = (pNumber - 1) * (qNumber - 1);
                EulerFunctionTextField.setText(String.valueOf(eulerFunction));
            } else {
                showErrorMessage("Числа p и q должны быть простыми");
                InputPTextField.clear();
                InputQTextField.clear();
                RTextField.clear();
                EulerFunctionTextField.clear();
                rNumber = null;
                eulerFunction = null;
            }
        }
    }

    @FXML
    public void inputD(ActionEvent event) {
        if (eulerFunction == null) {
            showErrorMessage("Пожалуйста, введите p и q");
        } else {
            if (InputDTextField.getText().isEmpty()) {
                showErrorMessage("Пожалуйста, заполните полe d");
            } else {
                if (isCorrectLongNumber(InputDTextField.getText())) {
                    dNumber = Long.parseLong(InputDTextField.getText());
                    eNumber = findENumber(eulerFunction, dNumber);
                    if (eNumber > 1 && eNumber < eulerFunction && gcd(eNumber, eulerFunction) == 1) {
                        ETextField.setText(String.valueOf(eNumber));
                    } else {
                        showErrorMessage("Число d не удовлетворяет условиями (число е не прошло проверку)");
                        InputDTextField.clear();
                        dNumber = null;
                        eNumber = null;
                    }
                } else {
                    showErrorMessage("Пожалуйста, проверьте корректность вводимых данных");
                }
            }
        }
    }

    @FXML
    public void inputSignCheckE(ActionEvent event) {
        if (InputSignCheckETextField.getText().isEmpty()) {
            showErrorMessage("Пожалуйста, введите e для проверки подписи");
        } else {
            if (isCorrectLongNumber(InputSignCheckETextField.getText())) {
                signCheckENumber = Long.parseLong(InputSignCheckETextField.getText());
            } else {
                showErrorMessage("Пожалуйста, проверьте корректность ввода");
            }
        }
    }

    @FXML
    public void inputSignCheckR(ActionEvent event) {
        if (InputSignCheckRTextField.getText().isEmpty()) {
            showErrorMessage("Пожалуйста, введите r для проверки подписи");
        } else {
            if (isCorrectLongNumber(InputSignCheckRTextField.getText())) {
                signCheckRNumber = Long.parseLong(InputSignCheckRTextField.getText());
            } else {
                showErrorMessage("Пожалуйста, проверьте корректность ввода");
            }
        }
    }

    // Subscribe & Verification
    @FXML
    public void subscribeFile(ActionEvent event) throws IOException {
        if (rNumber == null && dNumber == null) {
            showErrorMessage("Пожалуйста, введите p, q и d");
        } else {
            String fileData = readUnsignedFile();
            signHash = hashFunction(fileData, rNumber);
            sign = modExp(signHash, dNumber, rNumber);
            HashTextArea.setText(String.valueOf(signHash));
            SignTextArea.setText(String.valueOf(sign));
            writeToFile(new File("Подпись.txt"), fileData, sign);
        }
    }

    @FXML
    public void signVerification(ActionEvent event) throws IOException {
        SignCheckHashTextArea.clear();
        if (signCheckRNumber == null && signCheckENumber == null) {
            showErrorMessage("Пожалуйста, введите r и e для проверки подписи");
        } else {
            String[] fileData = readSignedFile();
            try {
                signCheckHash = hashFunction(fileData[0], signCheckRNumber);
                signCheckSign = modExp(Long.parseLong(fileData[1].replace(" ", "")), signCheckENumber, signCheckRNumber);
            }catch (ArrayIndexOutOfBoundsException e){
                showErrorMessage("Данный файл не содержит подписи");
                return;
            }
            SignCheckHashTextArea.setText(String.valueOf(signCheckHash));
            Alert alert;
            if(signCheckHash.equals(signCheckSign)){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Проверка подписи");
                alert.setHeaderText("Подпись действительна");
            }else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Проверка подписи");
                alert.setHeaderText("Подпись не действительна");
            }
            alert.showAndWait();
        }
    }


    public long hashFunction(String text, long r) {
        long resultHash = 100;
        for (int i = 0; i < text.length(); i++) {
            resultHash = modExp((resultHash + (byte) text.charAt(i)), 2, r);
        }
        return resultHash;
    }

    //  косячно работает
 /*   public long modExp(long x, long y, long N) {
        if (y == 0) return 1;
        long z = modExp(x, y / 2, N);
        if (y % 2 == 0)
            return (z * z) % N;
        else
            return (x * z * z) % N;
    }*/

    private long modExp(long m,long pow,long n){
        long a1 = m;
        long z1 = pow;
        long x = 1;
        while (z1 != 0){
            while (z1 % 2 == 0){
                z1/=2;
                a1 = (a1 * a1) % n;
            }
            z1--;
            x = (x*a1) % n;
        }
        return x;
    }

    // Работа с файлами
    public void writeToFile(File file, String message, long sign) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(message).append(" , ").append(sign);
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(stringBuilder.toString());
            writer.flush();
        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }

    private String readUnsignedFile() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        File file = chooseSourceFile();
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line);
        }
        br.close();
        return stringBuilder.toString();
    }

    private String[] readSignedFile() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        File file = chooseSourceFile();
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line);
        }
        br.close();
        //todo то что сказал в гс
        return stringBuilder.toString().split(" , ");
    }

    @FXML
    private File chooseSourceFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл");
        return fileChooser.showOpenDialog(new Stage());
    }
}
