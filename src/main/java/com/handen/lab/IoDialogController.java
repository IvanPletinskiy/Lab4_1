package com.handen.lab;

import com.handen.lab.model.RepositoryProxy;
import com.handen.lab.model.writers.BinaryEmployeesProvider;
import com.handen.lab.model.writers.CsvEmployeesProvider;
import com.handen.lab.model.writers.IOEmployeesProvider;
import com.handen.lab.model.writers.XmlIOEmployeesProvider;

import java.io.File;
import java.net.URL;
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
    private RepositoryProxy repository = RepositoryProxy.getInstance();

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

    private void saveToFile(IOEmployeesProvider provider) {
        File file = chooseFile("Save employees to directory", provider.getFileExtension());
        if(file != null) {
            provider.write(file, repository.getItems());
        }
    }

    public void onBinaryClicked(MouseEvent mouseEvent) {
        saveToFile(new BinaryEmployeesProvider());
    }

    public void onXmlClicked(MouseEvent mouseEvent) {
        saveToFile(new XmlIOEmployeesProvider());
    }

    public void onCsvClicked(MouseEvent mouseEvent) {
        saveToFile(new CsvEmployeesProvider());
    }
}