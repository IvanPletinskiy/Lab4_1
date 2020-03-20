package com.handen.lab.controller;

import com.handen.lab.data.Record;
import com.handen.lab.utils.LettersTextFormatter;
import com.handen.lab.utils.NumbersTextFormatter;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AddDialogController implements Initializable {
    public TextArea surname_text_area;
    public TextArea phone_text_area;
    public TextArea year_text_area;
    public Label errorLabel;
    public Button add_button;
    private AddRecordDialogListener mListener;
    private Stage mStage;

    public void setListener(AddRecordDialogListener listener) {
        mListener = listener;
    }

    public void setStage(Stage stage) {
        mStage = stage;
    }

    public void OnAddButtonClick(ActionEvent actionEvent) {
        boolean isValid = validate();
        if(isValid) {
            String surname = surname_text_area.textProperty().getValue();
            String year = year_text_area.textProperty().getValue();
            String phone = phone_text_area.textProperty().getValue();
            Record record = new Record(surname, phone, Short.parseShort(year));
            mListener.addRecord(record);
            if(mStage != null) {
                mStage.hide();
            }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        surname_text_area.setTextFormatter(new LettersTextFormatter());
        phone_text_area.setTextFormatter(new NumbersTextFormatter(12));
        year_text_area.setTextFormatter(new NumbersTextFormatter(4));
    }

    interface AddRecordDialogListener {
        void addRecord(Record record);
    }

    private void showError(String errorText) {
        errorLabel.setText(errorText);
        errorLabel.setVisible(true);
    }
}
