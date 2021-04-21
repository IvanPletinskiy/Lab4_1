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
    private final StringBuilder resultEncrypt = new StringBuilder();
    private final StringBuilder resultDecrypt = new StringBuilder();
    public ComboBox<Long> combobox;
    public Label errorLabel;
    private String extension;
    private File sourceFile;
    private byte[] sourceFileData;
    private Long pNumber;
    private Long kNumber;
    private Long xNumber;
    private Long gNumber;
    private ArrayList<Long> roots;
    private final ObservableList<Long> gRoots = FXCollections.observableArrayList();

    @FXML
    private Text messageText;

    @FXML
    private TextField NumberPTextField;

    @FXML
    private TextField NumberKTextField;

    @FXML
    private TextField NumberXTextField;

    @FXML
    private TextArea resultFileDataTextArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combobox.setItems(gRoots);
        combobox.setOnAction(actionEvent -> {
            gNumber = combobox.getValue();
        });
        NumberPTextField.setTextFormatter(new NumbersTextFormatter(20));
        NumberXTextField.setTextFormatter(new NumbersTextFormatter(20));
        NumberKTextField.setTextFormatter(new NumbersTextFormatter(20));
    }

    String getExtension(String fileName) {
        String extension = "";
        String[] array = fileName.split("\\.");
        extension = array[array.length - 1];
        return extension;
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
            messageText.setText("File data is taken");
            messageText.setFill(Color.GREEN);
            messageText.setVisible(true);
        }
    }

    @FXML
    void clearAllData(ActionEvent event) {
        roots = null;
        gNumber = null;
        pNumber = null;
        kNumber = null;
        xNumber = null;
        sourceFile = null;
        sourceFileData = null;
        gRoots.clear();
        NumberXTextField.clear();
        NumberKTextField.clear();
        NumberPTextField.clear();
    }

    private boolean validateKNumber() {
        if(NumberKTextField.getText().isBlank()) {
            errorLabel.setText("K is empty");
            return false;
        }

        long k = Long.parseLong(NumberKTextField.getText());
        boolean result = k > 1 && k < pNumber - 1 && findGCD(k, pNumber) == 1;
        errorLabel.setText("K is invalid");
        return result;
    }

    private boolean validateXNumber() {
        if(NumberXTextField.getText().isBlank()) {
            errorLabel.setText("X is empty");
            return false;
        }

        long x = Long.parseLong(NumberXTextField.getText());
        boolean result = x > 1 && x < pNumber - 1;
        errorLabel.setText("X is invalid");
        return result;
    }

    @FXML
    void onValidatePNumberClicked(ActionEvent event) {
        boolean result = validatePNumber();
        messageText.setVisible(true);
        if(result) {
            pNumber = Long.parseLong(NumberPTextField.getText());
            roots = findPrimitiveRoots(pNumber);
            gRoots.setAll(roots);
            messageText.setFill(Color.GREEN);
            messageText.setText("Number P is taken!");
        }
        else {
            messageText.setText("Invalid input, please, try again");
            messageText.setFill(Color.RED);
        }
    }

    private boolean validatePNumber() {
        if(NumberPTextField.getText().isBlank()) {
            errorLabel.setText("P is empty");
            return false;
        }
        long p = Long.parseLong(NumberPTextField.getText());
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

    private boolean validateInputFile() {
        boolean result = sourceFileData != null;
        if(!result) {
            errorLabel.setText("Source file is not set.");
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

    // Нахождение НОД двух чисел с помощью алгоритма Эвклида
    private long findGCD(long firstNumber, long secondNumber) {
        if(secondNumber == 0) {
            return firstNumber;
        }
        return findGCD(secondNumber, firstNumber % secondNumber);
    }

    // Быстрое возведение в степень a ^ z mod n
    private long fastExpMod(long firstNumber, long secondNumber, long modNumber) {
        if(secondNumber == 0) {
            return 1;
        }
        if(secondNumber % 2 == 0) {
            long temp = fastExpMod(firstNumber, secondNumber / 2, modNumber);
            return helperFastExpMod(temp, temp, modNumber) % modNumber;
        }
        return (helperFastExpMod(fastExpMod(firstNumber, secondNumber - 1, modNumber), firstNumber, modNumber)) % modNumber;

    }

    private long helperFastExpMod(long firstNumber, long secondNumber, long modNumber) {
        if(secondNumber == 1) {
            return firstNumber;
        }
        if(secondNumber == 0) {
            return 1;
        }
        if(secondNumber % 2 == 0) {
            long temp = helperFastExpMod(firstNumber, secondNumber / 2, modNumber);
            return (2 * temp) % modNumber;
        }
        return (helperFastExpMod(firstNumber, secondNumber - 1, modNumber) + firstNumber) % modNumber;
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

    @FXML
    void encryptSourceData(ActionEvent event) throws IOException {
        messageText.setVisible(false);
        if(!(validateXNumber() && validateKNumber() && validatePNumber())) {
            errorLabel.setVisible(true);
            return;
        }
        else {
            errorLabel.setVisible(false);
        }

        kNumber = Long.parseLong(NumberKTextField.getText());
        xNumber = Long.parseLong(NumberXTextField.getText());

        if(pNumber != null && gNumber != null && sourceFileData != null) {
            long a = fastExpMod(gNumber, kNumber, pNumber);
            long b;
            long y = fastExpMod(gNumber, xNumber, pNumber);
            ArrayList<Long> output = new ArrayList<>();
            for(int i = 0; i < sourceFileData.length; i++) {
                b = ((fastExpMod(y, kNumber, pNumber) * (sourceFileData[i] % pNumber)) % pNumber);
                if(i != sourceFileData.length - 1) {
                    resultEncrypt.append(a);
                    resultEncrypt.append(" ");
                    resultEncrypt.append(b);
                    resultEncrypt.append(" ");
                    output.add(a);
                    output.add(b);
                }
                else {
                    resultEncrypt.append(a);
                    resultEncrypt.append(" ");
                    resultEncrypt.append(b);
                }
            }
            //            String subText = resultEncrypt.toString().length() > 1000 ? resultEncrypt.substring(0, 1000) : resultEncrypt.toString();
            resultFileDataTextArea.setText(resultEncrypt.substring(0, 1000));
            writeDataToFile(output, "зашфированный файл." + extension);
            messageText.setText("Cipher is complete");
            messageText.setFill(Color.GREEN);
        }
        else {
            messageText.setText("Enter, please, all numbers\nand choose source file.");
            messageText.setFill(Color.RED);
        }
    }

    @FXML
    void decryptSourceData(ActionEvent event) throws IOException {
        if(!resultEncrypt.toString().isEmpty()) {
            long m;
            String[] temp = resultEncrypt.toString().split(" ");
            ArrayList<Long> decryptArray = new ArrayList<>();
            ArrayList<Long> output = new ArrayList<>();
            for(String s : temp) {
                decryptArray.add(Long.parseLong(s));
            }
            for(int i = 0; i < decryptArray.size() - 1; i += 2) {
                long a = decryptArray.get(i);
                long b = decryptArray.get(i + 1);
                m = (fastExpMod(a, xNumber * (pNumber - 2), pNumber) * (b % pNumber)) % pNumber;
                output.add(m);
                resultDecrypt.append(m);
                resultDecrypt.append(" ");
            }
            writeDataToFile(output, "дешифрованный файл." + extension);
            messageText.setText("Decipher is complete");
            messageText.setFill(Color.GREEN);
        }
        else {
            messageText.setText("Please, encrypt something.");
            messageText.setFill(Color.RED);
        }
        messageText.setVisible(true);
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
}