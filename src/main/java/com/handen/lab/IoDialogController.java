package com.handen.lab;

import com.handen.lab.data.Employee;
import com.handen.lab.model.RepositoryProxy;
import com.handen.lab.model.writers.BinaryEmployeesMapper;
import com.handen.lab.model.writers.CsvEmployeesMapper;
import com.handen.lab.model.writers.EmployeesMapper;
import com.handen.lab.model.writers.XmlEmployeesMapper;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class IoDialogController implements Initializable {
    public Button binary_button;
    public Button xml_button;
    public Button csv_button;
    public Label title;
    private Stage stage;
    private final RepositoryProxy repository = RepositoryProxy.getInstance();
    private IOMode ioMode;

    public void setIoMode(IOMode ioMode) {
        this.ioMode = ioMode;
        if(ioMode.equals(IOMode.LOAD)) {
            title.setText("LOAD");
        }
        else {
            title.setText("SAVE");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private File chooseFile(String title, String extension) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        String userDirectoryString = System.getProperty("user.home");
        File userDirectory = new File(userDirectoryString);
        if(!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }
        fileChooser.setInitialDirectory(userDirectory);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(extension, "*." + extension));
        return fileChooser.showOpenDialog(stage);
    }

    private File chooseDirecotory(String title) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(title);

        String userDirectoryString = System.getProperty("user.home");
        File userDirectory = new File(userDirectoryString);
        if(!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }

        directoryChooser.setInitialDirectory(userDirectory);
        return directoryChooser.showDialog(stage);
    }

    private void saveToFile(EmployeesMapper mapper) {
        File file = chooseFile("Save employees to file", mapper.getFileExtension());
        if(file != null) {
            mapper.write(file, repository.getItems());
        }
    }

    private void loadFromFile(EmployeesMapper mapper) {
        File file = chooseFile("Load employees from file", mapper.getFileExtension());
        if(file != null) {
            List<Employee> employeeList = mapper.read(file);
            repository.getItems().setAll(employeeList);
        }
    }

    public void onBinaryClicked(MouseEvent mouseEvent) {
        EmployeesMapper mapper = new BinaryEmployeesMapper();
        if(ioMode == IOMode.SAVE) {
            saveToFile(mapper);
        }
        else {
            loadFromFile(mapper);
        }
    }

    public void onXmlClicked(MouseEvent mouseEvent) {
        String pluginPath = "";
        EmployeesMapper mapper = new XmlEmployeesMapper(pluginPath);
        if(ioMode == IOMode.SAVE) {
            saveToFile(mapper);
        }
        else {
            loadFromFile(mapper);
        }
    }

    public void onCsvClicked(MouseEvent mouseEvent) {
        EmployeesMapper mapper = new CsvEmployeesMapper();
        if(ioMode == IOMode.SAVE) {
            saveToFile(mapper);
        }
        else {
            loadFromFile(mapper);
        }
    }

    public enum IOMode {
        SAVE,
        LOAD
    }
}