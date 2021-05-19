package com.handen.lab.controller;

import com.handen.lab.OnSendNotificationCallback;

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

    private OnSendNotificationCallback callback = new OnSendNotificationCallback() {
        @Override
        public void onFailure(String message) {
            showError(message);
        }
    };

    public void onSendButtonClicked(ActionEvent actionEvent) {
        error_label.setVisible(false);
        boolean sendingNow = now_check_box.isSelected();
        String title = title_text_field.getText();
        String message = message_text_field.getText();

        boolean result = validate_input(title, message);

        if(result) {
            if(sendingNow) {
                sendNow(title, message, callback);
            }
            else {
                boolean validate_date_result = validate_date_input();
                if(validate_date_result) {
                    sendWithDelay(title, message);
                }
                else {
                    showError("Некорректный ввод времени отправки");
                }
            }
        }
        else {
            showError("Некорректный ввод");
        }
    }

    private void sendWithDelay(String title, String message) {
        Date date = getDatepickerDate();
        long timeMillis = parseTime();

        long sendMillis = date.getTime() + timeMillis;
        sendAtTime(title, message, sendMillis, callback);
    }

    private Date getDatepickerDate() {
        LocalDate localDate = date_picker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }

    private long parseTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String timeString = time_text_field.getText();
        long timeMillis = -1;
        try {
            Date timeDate = dateFormat.parse(timeString);
            timeMillis = timeDate.getTime();
        }
        catch(ParseException exception) {
            exception.printStackTrace();
        }
        return timeMillis;
    }

    private boolean validate_date_input() {
        return true;
    }

    private boolean validate_input(String title, String message) {
        return true;
    }

    private void showError(String message) {
        error_label.setText(message);
        error_label.setVisible(true);
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
}