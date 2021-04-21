package com.handen.lab.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
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
    public ComboBox<Long> g_number_combobox;
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
        g_number_combobox.setItems(gRoots);
        g_number_combobox.setOnAction(actionEvent -> {
            gNumber = g_number_combobox.getValue();
        });
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
        if (sourceFile != null) {
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

    private boolean isCorrectPNumber(String number) {
        long result;
        try {
            result = Long.parseLong(number);
        } catch (Exception e) {
            return false;
        }
        return result > 255 && simplyNumberFermaTest(result);
    }

    private boolean isCorrectKNumber(String number) {
        long result;
        try {
            result = Long.parseLong(number);
        } catch (Exception e) {
            return false;
        }
        return result > 1 && result < pNumber - 1 && findGCD(result, pNumber) == 1;
    }

    private boolean isCorrectXNumber(String number) {
        long result;
        try {
            result = Long.parseLong(number);
        } catch (Exception e) {
            return false;
        }
        return result > 1 && result < pNumber - 1;
    }

    @FXML
    void inputPNumber(ActionEvent event) {
        messageText.setText("Invalid input, please, try again");
        messageText.setFill(Color.RED);
        messageText.setVisible(false);
        if (isCorrectPNumber(NumberPTextField.getText())) {
            pNumber = Long.parseLong(NumberPTextField.getText());
            roots = findPrimitiveRoots(pNumber);
            gRoots.setAll(roots);
            messageText.setFill(Color.GREEN);
            messageText.setText("Number P is taken!");
        }
        messageText.setVisible(true);
    }

    @FXML
    void inputKNumber(ActionEvent event) {
        messageText.setText("Invalid input, please, try again");
        messageText.setFill(Color.RED);
        messageText.setVisible(false);
        if (pNumber != null) {
            if (isCorrectKNumber(NumberKTextField.getText())) {
                kNumber = Long.parseLong(NumberKTextField.getText());
                messageText.setFill(Color.GREEN);
                messageText.setText("Number K is taken!");
            } else {
                messageText.setText("Invalid input, please, try again");
                messageText.setFill(Color.RED);
            }
        } else {
            messageText.setText("Enter, please, P number");
            messageText.setFill(Color.RED);
        }
        messageText.setVisible(true);
    }

    @FXML
    void inputXNumber(ActionEvent event) {
        messageText.setText("Invalid input, please, try again");
        messageText.setFill(Color.RED);
        messageText.setVisible(false);
        if (pNumber != null) {
            if (isCorrectXNumber(NumberXTextField.getText())) {
                xNumber = Long.parseLong(NumberXTextField.getText());
                messageText.setFill(Color.GREEN);
                messageText.setText("Number X is taken!");
            } else {
                messageText.setText("Invalid input, please, try again");
                messageText.setFill(Color.RED);
            }
        } else {
            messageText.setText("Enter, please, P number");
            messageText.setFill(Color.RED);
        }
        messageText.setVisible(true);
    }

    // Находим все простые делители числа
    private ArrayList<Long> findSimplyDividers(long number) {
        ArrayList<Long> arrayList = new ArrayList<>();
        for (long i = 2; i * i <= number; ++i) {
            if (number % i == 0) {
                arrayList.add(i);
                while (number % i == 0) {
                    number /= i;
                }
            }
        }
        if (number != 1) {
            arrayList.add(number);
        }
        return arrayList;
    }

    // Нахождение НОД двух чисел с помощью алгоритма Эвклида
    private long findGCD(long firstNumber, long secondNumber) {
        if (secondNumber == 0) {
            return firstNumber;
        }
        return findGCD(secondNumber, firstNumber % secondNumber);
    }

    // Проверка на простоту, вероятностный тест Ферма
    private boolean simplyNumberFermaTest(long number) {
        if (number == 2) {
            return true;
        }
        for (int i = 0; i < 100; i++) {
            long temp = (long) ((Math.random() % (number - 2)) + 2);
            if (findGCD(temp, number) != 1) {
                return false;
            }
            if (fastExpMod(temp, number - 1, number) != 1) {
                return false;
            }
        }
        return true;
    }

    // Быстрое возведение в степень a ^ z mod n
    private long fastExpMod(long firstNumber, long secondNumber, long modNumber) {
        if (secondNumber == 0) {
            return 1;
        }
        if (secondNumber % 2 == 0) {
            long temp = fastExpMod(firstNumber, secondNumber / 2, modNumber);
            return helperFastExpMod(temp, temp, modNumber) % modNumber;
        }
        return (helperFastExpMod(fastExpMod(firstNumber, secondNumber - 1, modNumber), firstNumber, modNumber)) % modNumber;

    }

    private long helperFastExpMod(long firstNumber, long secondNumber, long modNumber) {
        if (secondNumber == 1) {
            return firstNumber;
        }
        if (secondNumber == 0) {
            return 1;
        }
        if (secondNumber % 2 == 0) {
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
        for (long g = 2; g < p; g++) {
            flag = true;
            for (int j = 0; j < simplyDividers.size(); j++) {
                if (fastExpMod(g, (p - 1) / simplyDividers.get(j), p) == 1) {
                    flag = false;
                } else {
                    if ((j == simplyDividers.size() - 1) && flag) {
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
        if (pNumber != null && kNumber != null && xNumber != null && gNumber != null && sourceFileData != null) {
            long a = fastExpMod(gNumber, kNumber, pNumber);
            long b;
            long y = fastExpMod(gNumber, xNumber, pNumber);
            ArrayList<Long> output = new ArrayList<>();
            for (int i = 0; i < sourceFileData.length; i++) {
                b = ((fastExpMod(y, kNumber, pNumber) * (sourceFileData[i] % pNumber)) % pNumber);
                if (i != sourceFileData.length - 1) {
                    resultEncrypt.append(a);
                    resultEncrypt.append(" ");
                    resultEncrypt.append(b);
                    resultEncrypt.append(" ");
                    output.add(a);
                    output.add(b);
                } else {
                    resultEncrypt.append(a);
                    resultEncrypt.append(" ");
                    resultEncrypt.append(b);
                }
            }
            String subText = resultEncrypt.toString().length() > 1000 ? resultEncrypt.substring(0, 1000) : resultEncrypt.toString();
            resultFileDataTextArea.setText(subText);
            writeDataToFile(output, "зашфированный файл." + extension);
            messageText.setText("Cipher is complete");
            messageText.setFill(Color.GREEN);
        } else {
            messageText.setText("Enter, please, all numbers\nand choose source file.");
            messageText.setFill(Color.RED);
        }
        messageText.setVisible(true);
    }

    @FXML
    void decryptSourceData(ActionEvent event) throws IOException {
        if (!resultEncrypt.toString().isEmpty()) {
            long m;
            String[] temp = resultEncrypt.toString().split(" ");
            ArrayList<Long> decryptArray = new ArrayList<>();
            ArrayList<Long> output = new ArrayList<>();
            for (String s : temp) {
                decryptArray.add(Long.parseLong(s));
            }
            for (int i = 0; i < decryptArray.size() - 1; i += 2) {
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
        } else {
            messageText.setText("Please, encrypt something.");
            messageText.setFill(Color.RED);
        }
        messageText.setVisible(true);
    }

    // Записываем в файл результат
    void writeDataToFile(ArrayList<Long> data, String fileName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            byte[] bytes = new byte[data.size()];
            for (int i = 0; i < data.size(); i++) {
                bytes[i] = data.get(i).byteValue();
            }
            fos.write(bytes, 0, bytes.length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
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