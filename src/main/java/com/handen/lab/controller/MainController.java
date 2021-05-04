package com.handen.lab.controller;

import com.handen.lab.utils.NumbersTextFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Text messageText;
    @FXML
    private TextField pTextField;
    @FXML
    private TextField kTextField;
    @FXML
    private TextField xTextField;
    @FXML
    private TextArea outputTextArea;
    public ComboBox<Long> combobox;
    public Label errorLabel;

    private Long p;
    private Long k;
    private Long x;
    private Long g;
    private final ObservableList<Long> gRoots = FXCollections.observableArrayList();

    private String extension;
    private File sourceFile;
    private byte[] sourceFileData;

    private StringBuilder encryptStringBuilder = new StringBuilder();
    private StringBuilder decryptStringBuilder = new StringBuilder();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combobox.setItems(gRoots);
        combobox.setOnAction(actionEvent -> {
            g = combobox.getValue();
        });
        pTextField.setTextFormatter(new NumbersTextFormatter(20));
        xTextField.setTextFormatter(new NumbersTextFormatter(20));
        kTextField.setTextFormatter(new NumbersTextFormatter(20));
    }

    private boolean validateKNumber() {
        if(kTextField.getText().isBlank()) {
            errorLabel.setText("K is empty");
            return false;
        }

        long k = Long.parseLong(kTextField.getText());
        boolean result = k > 1 && k < p - 1 && findGCD(k, p) == 1;
        errorLabel.setText("K is invalid");
        return result;
    }

    private boolean validateXNumber() {
        if(xTextField.getText().isBlank()) {
            errorLabel.setText("X is empty");
            return false;
        }

        long x = Long.parseLong(xTextField.getText());
        boolean result = x > 1 && x < p - 1;
        errorLabel.setText("X is invalid");
        return result;
    }

    private boolean validateInput() {
        boolean result = sourceFileData != null;
        if(!result) {
            errorLabel.setText("File is not selected");
        }
        return result;
    }

    @FXML
    void onValidatePNumberClicked(ActionEvent event) {
        boolean result = validatePNumber();
        if(result) {
            errorLabel.setVisible(false);
            p = Long.parseLong(pTextField.getText());
            gRoots.setAll(findPrimitiveRoots(p));
            messageText.setVisible(true);
            messageText.setFill(Color.GREEN);
            messageText.setText("Number P is valid");
        }
        else {
            errorLabel.setVisible(true);
        }
    }

    private boolean validatePNumber() {
        if(pTextField.getText().isBlank()) {
            errorLabel.setText("P is empty");
            return false;
        }
        long p = Long.parseLong(pTextField.getText());
        boolean result = p > 255 && validateNumberIsPrime(p);
        errorLabel.setText("P is invalid");
        return result;
    }

    //Вероятностный тест Ферма
    private boolean validateNumberIsPrime(long number) {
        if(number == 2) {
            return true;
        }
        for(int i = 0; i < 100; i++) {
            long temp = (long) ((Math.random() % (number - 2)) + 2);
            if(findGCD(temp, number) != 1) {
                return false;
            }
            if(fastExpMod(temp, number - 1, number) != 1) {
                return false;
            }
        }
        return true;
    }

    // Нахождение НОД двух чисел с помощью алгоритма Эвклида
    private long findGCD(long firstNumber, long secondNumber) {
        if(secondNumber == 0) {
            return firstNumber;
        }
        return findGCD(secondNumber, firstNumber % secondNumber);
    }

    // Быстрое возведение в степень a ^ z mod n
    private long fastExpMod(long base, long power, long mod) {
        if(power == 0) {
            return 1;
        }
        if(power % 2 == 0) {
            long temp = fastExpMod(base, power / 2, mod);
            return helperFastExpMod(temp, temp, mod) % mod;
        }
        return (helperFastExpMod(fastExpMod(base, power - 1, mod), base, mod)) % mod;
    }

    private long helperFastExpMod(long base, long power, long mod) {
        if(power == 1) {
            return base;
        }
        if(power == 0) {
            return 1;
        }
        if(power % 2 == 0) {
            long temp = helperFastExpMod(base, power / 2, mod);
            return (2 * temp) % mod;
        }
        return (helperFastExpMod(base, power - 1, mod) + base) % mod;
    }

    // Поиск первообразных корней по модулю p
    ArrayList<Long> findPrimitiveRoots(long p) {
        ArrayList<Long> simplyDividers = findSimplyDividers(p - 1);
        ArrayList<Long> result = new ArrayList<>();
        boolean flag;
        for(long g = 2; g < p; g++) {
            flag = true;
            for(int j = 0; j < simplyDividers.size(); j++) {
                if(fastExpMod(g, (p - 1) / simplyDividers.get(j), p) == 1) {
                    flag = false;
                }
                else {
                    if((j == simplyDividers.size() - 1) && flag) {
                        result.add(g);
                    }
                }
            }
        }
        return result;
    }

    // Находим все простые делители числа
    private ArrayList<Long> findSimplyDividers(long number) {
        ArrayList<Long> arrayList = new ArrayList<>();
        for(long i = 2; i * i <= number; ++i) {
            if(number % i == 0) {
                arrayList.add(i);
                while(number % i == 0) {
                    number /= i;
                }
            }
        }
        if(number != 1) {
            arrayList.add(number);
        }
        return arrayList;
    }

    @FXML
    void encryptSourceData(ActionEvent event) throws IOException {
        encryptStringBuilder = new StringBuilder();
        boolean result = validateFields();
        if(!result) {
            return;
        }

        k = Long.parseLong(kTextField.getText());
        x = Long.parseLong(xTextField.getText());

        if(p != null && g != null && sourceFileData != null) {
            long a = fastExpMod(g, k, p);
            long b;
            long y = fastExpMod(g, x, p);
            ArrayList<Long> output = new ArrayList<>();
            for(int i = 0; i < sourceFileData.length; i++) {
                b = fastExpMod(y, k, p) * (sourceFileData[i] % p) % p;
                if(i != sourceFileData.length - 1) {
                    encryptStringBuilder.append(a);
                    encryptStringBuilder.append(" ");
                    encryptStringBuilder.append(b);
                    encryptStringBuilder.append(" ");
                    output.add(a);
                    output.add(b);
                }
                else {
                    encryptStringBuilder.append(a);
                    encryptStringBuilder.append(" ");
                    encryptStringBuilder.append(b);
                }
            }

            int outputTextLength = encryptStringBuilder.length();
            if(outputTextLength > 1000) {
                outputTextLength = 1000;
            }

            outputTextArea.setText(encryptStringBuilder.substring(0, outputTextLength));
            writeDataToFile(output, "encrypted." + extension);
            messageText.setText("Encrypting is complete");
            messageText.setFill(Color.GREEN);
        }
        else {
            errorLabel.setText("Enter, please, all numbers\nand choose source file.");
            errorLabel.setVisible(true);
        }
    }

    private boolean validateFields() {
        messageText.setVisible(false);
        if(!(validateXNumber() && validateKNumber() && validatePNumber() && validateInput() && validateGNumber())) {
            errorLabel.setVisible(true);
            return false;
        }
        else {
            errorLabel.setVisible(false);
        }
        return true;
    }

    private boolean validateGNumber() {
        boolean result = combobox.getValue() != null;
        if(!result) {
            errorLabel.setText("G is not set");
        }
        return result;
    }

    @FXML
    void decryptSourceData(ActionEvent event) throws IOException {
        decryptStringBuilder = new StringBuilder();
        if(!encryptStringBuilder.toString().isEmpty()) {
            long m;
            String[] temp = encryptStringBuilder.toString().split(" ");
            ArrayList<Long> decryptArray = new ArrayList<>();
            ArrayList<Long> output = new ArrayList<>();
            for(String s : temp) {
                decryptArray.add(Long.parseLong(s));
            }
            for(int i = 0; i < decryptArray.size() - 1; i += 2) {
                long a = decryptArray.get(i);
                long b = decryptArray.get(i + 1);
                m = (fastExpMod(a, x * (p - 2), p) * (b % p)) % p;
                output.add(m);
                decryptStringBuilder.append(m);
                decryptStringBuilder.append(" ");
            }

            int outputTextLength = decryptStringBuilder.length();
            if(outputTextLength > 1000) {
                outputTextLength = 1000;
            }

            outputTextArea.setText(decryptStringBuilder.substring(0, outputTextLength));

            writeDataToFile(output, "decrypted." + extension);
        }
        else {
            errorLabel.setText("Please, encrypt something.");
            errorLabel.setVisible(true);
        }
    }

    // Записываем в файл результат
    void writeDataToFile(ArrayList<Long> data, String fileName) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(fileName)) {
            byte[] bytes = new byte[data.size()];
            for(int i = 0; i < data.size(); i++) {
                bytes[i] = data.get(i).byteValue();
            }
            fos.write(bytes, 0, bytes.length);
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void chooseSourceFile(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл");
        Stage stage = new Stage();
        sourceFile = fileChooser.showOpenDialog(stage);
        if(sourceFile != null) {
            extension = getExtension(sourceFile.getPath());
            sourceFileData = Files.readAllBytes(Paths.get(sourceFile.getPath()));
        }
    }

    String getExtension(String fileName) {
        String extension = "";
        String[] array = fileName.split("\\.");
        extension = array[array.length - 1];
        return extension;
    }

    @FXML
    void clearAllData(ActionEvent event) {
        g = null;
        p = null;
        k = null;
        x = null;
        errorLabel.setVisible(false);
        messageText.setVisible(false);
        sourceFile = null;
        sourceFileData = null;
        gRoots.clear();
        xTextField.clear();
        kTextField.clear();
        pTextField.clear();
    }
}