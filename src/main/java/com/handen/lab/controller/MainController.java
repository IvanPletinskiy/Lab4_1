package com.handen.lab.controller;

import com.handen.lab.App;
import com.handen.lab.data.Record;
import com.handen.lab.model.BaseModel;
import com.handen.lab.model.Model;
import com.handen.lab.utils.LettersTextFormatter;
import com.handen.lab.utils.NumbersTextFormatter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController implements Initializable {

    private Stage stage;

    public Label errorLabel;
    private ObservableList<Record> items = FXCollections.observableArrayList();

    public TextArea search_year;

    public TextArea search_surname;

    public TableView table;

    public TextArea year_text_area;
    public TextArea phone_text_area;
    public TextArea surname_text_area;

    public Button add_button;
    public Button save_button;
    public Button delete_button;
    public MenuItem menu_load;
    public MenuItem menu_save;
    public Menu menu_help;
    public Menu menu_about;

    private int selectedRow;

    private BaseModel mModel;

    private RecordChangeListener recordChangedListener = () -> {
        Record item = items.get(selectedRow);
        boolean isChanged = !surname_text_area.textProperty().getValue().equals(item.getSurname()) ||
                !year_text_area.textProperty().getValue().equals(item.getYearString()) ||
                !phone_text_area.textProperty().getValue().equals(item.getPhone());

        save_button.setVisible(isChanged);
    };

    @FXML public void OnAddButtonClick(ActionEvent actionEvent) {

        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("add_dialog.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add record");
            stage.setScene(new Scene(root, 450, 450));
            AddDialogController controller = loader.getController();
            controller.setStage(stage);
            controller.setListener(record -> {
                if(record != null) {
                    addItem(record);
                }
            });
            stage.show();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void addItem(Record record) {
        items.add(record);
        table.getSelectionModel().select(items.size() - 1);
    }

    public void OnSaveButtonClick(ActionEvent actionEvent) {
        boolean isValid = validate();
        if(isValid) {
            String surname = surname_text_area.textProperty().getValue();
            String year = year_text_area.textProperty().getValue();
            String phone = phone_text_area.textProperty().getValue();
            Record record = new Record(surname, phone, Short.parseShort(year));
            items.set(selectedRow, record);
        }
    }

    private boolean validate() {
        boolean isValid = true;

        short year = getYear();
        if(year < 2000 || year > 2020) {
            isValid = false;
            showError("Year cannot be less than 2000 or grater than 2020");
        }

        if(surname_text_area.textProperty().getValue().equals("")) {
            isValid = false;
            showError("Surname field cannot be empty.");
        }
        if(phone_text_area.textProperty().getValue().equals("")) {
            isValid = false;
            showError("Phone field cannot be empty.");
        }
        if(year_text_area.textProperty().getValue().equals("")) {
            isValid = false;
            showError("Year field cannot be empty.");
        }

        return isValid;
    }

    private short getYear() {
        short year = -1;
        try {
            year = Short.parseShort(year_text_area.textProperty().getValue());
        }
        catch(NumberFormatException e) {
            e.printStackTrace();
        }
        return year;
    }

    private void showError(String errorText) {
        errorLabel.setText(errorText);
        errorLabel.setVisible(true);
    }

    public void OnDeleteButtonClick(ActionEvent actionEvent) {
        items.remove(selectedRow);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindTextFormatters();
        bindTextListeners();
        initializeTable();

        mModel = new Model();
    }

    private void initializeTable() {
        table.setEditable(false);
        items.add(new Record("hahahandenA", "375293190365", (short) 2012));
        items.add(new Record("hahahandenB", "375293190366", (short) 2013));
        items.add(new Record("hahahandenC", "375293190367", (short) 2014));
        table.setItems(items);
        table.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int newSelectedId = (int) newValue;
            Record item = items.get(newSelectedId);
            updateTextAreas(item);
            selectedRow = newSelectedId;
            save_button.setVisible(false);
            errorLabel.setVisible(false);
        });

        table.getSelectionModel().clearAndSelect(0);
    }

    private void updateTextAreas(Record item) {
        surname_text_area.setText(item.getSurname());
        year_text_area.setText(item.getYearString());
        phone_text_area.setText(item.getPhone());
    }

    private void bindTextFormatters() {
        search_surname.setTextFormatter(new LettersTextFormatter());
        search_year.setTextFormatter(new NumbersTextFormatter(4));

        surname_text_area.setTextFormatter(new LettersTextFormatter());
        phone_text_area.setTextFormatter(new NumbersTextFormatter(12));
        year_text_area.setTextFormatter(new NumbersTextFormatter(4));
    }

    private void bindTextListeners() {
        surname_text_area.textProperty().addListener((observable, oldValue, newValue) -> {
            recordChangedListener.onChanged();
        });
        year_text_area.textProperty().addListener((observable, oldValue, newValue) -> {
            recordChangedListener.onChanged();
        });
        phone_text_area.textProperty().addListener((observable, oldValue, newValue) -> {
            recordChangedListener.onChanged();
        });
    }

    public void OnMenuLoadClicked(ActionEvent actionEvent) {
        File file = chooseFile("Open records file");
        if(file != null) {
            mModel.loadRecords(file);
        }
    }

    public void OnMenuSaveClicked(ActionEvent actionEvent) {
       File file = chooseFile("Save records to file");
        if(file != null) {
            mModel.loadRecords(file);
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
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        return fileChooser.showOpenDialog(stage);
    }

    public void OnMenuHelpClicked(ActionEvent actionEvent) {
        //TODO
    }

    public void OnMenuAboutClicked(ActionEvent actionEvent) {
        //TODO
    }

    public void OnSearchYearKeyTyped(KeyEvent keyEvent) {

    }

    interface RecordChangeListener {
        void onChanged();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}