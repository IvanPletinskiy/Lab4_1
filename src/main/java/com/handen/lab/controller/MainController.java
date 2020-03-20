package com.handen.lab.controller;

import com.handen.lab.data.Record;
import com.handen.lab.utils.LettersTextFormatter;
import com.handen.lab.utils.NumbersTextFormatter;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

public class MainController implements Initializable {

    private ObservableList<Record> items = FXCollections.observableArrayList();

    public TextArea search_year;

    public TextArea search_surname;

    public TableView table;

    public TextArea year_text_area;
    public TextArea phone_text_area;
    public TextArea surname_text_area;

    public Button add_button;
    public Button edit_button;
    public Button delete_button;
    public MenuItem menu_load;
    public MenuItem menu_save;
    public Menu menu_help;
    public Menu menu_about;

    @FXML public void OnAddButtonClick(ActionEvent actionEvent) {
        //TODO
    }

    @FXML public void OnEditButtonClick(ActionEvent actionEvent) {
        //TODO
    }

    @FXML public void OnDeleteButtonClick(ActionEvent actionEvent) {
        //TODO
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindTextFormatters();
        initializeTable();
    }

    private void initializeTable() {
        table.setEditable(false);
        items.add(new Record("hahahanden", "375293190365", (short) 2012));
        items.add(new Record("hahahanden2", "375293190366", (short) 2013));
        items.add(new Record("hahahanden3", "375293190367", (short) 2014));
        items.add(new Record());
        table.setItems(items);

        table.getSelectionModel().clearAndSelect(0);
        //table.getFocusModel().focus(0);
    }

    private void bindTextFormatters() {
        search_surname.setTextFormatter(new LettersTextFormatter());
        search_year.setTextFormatter(new NumbersTextFormatter(4));

        surname_text_area.setTextFormatter(new LettersTextFormatter());
        phone_text_area.setTextFormatter(new NumbersTextFormatter(12));
        year_text_area.setTextFormatter(new NumbersTextFormatter(4));
    }

    public void OnMenuLoadClicked(ActionEvent actionEvent) {
        //TODO
    }

    public void OnMenuSaveClicked(ActionEvent actionEvent) {
        //TODO
    }

    public void OnMenuHelpClicked(ActionEvent actionEvent) {
        //TODO
    }

    public void OnMenuAboutClicked(ActionEvent actionEvent) {
        //TODO
    }

    public void OnSearchYearKeyTyped(KeyEvent keyEvent) {

    }
}