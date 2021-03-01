package com.handen.lab.controller;

import com.handen.lab.data.Employee;
import com.handen.lab.data.designer.UIDesigner;
import com.handen.lab.data.designer.UXDesigner;
import com.handen.lab.data.developer.BackendDeveloper;
import com.handen.lab.data.developer.FrontendDeveloper;
import com.handen.lab.data.developer.MobileDeveloper;
import com.handen.lab.data.managers.DepartmentManager;
import com.handen.lab.data.managers.ProjectManager;
import com.handen.lab.model.RepositoryProxy;
import com.handen.lab.utils.LettersTextFormatter;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AddDialogController implements Initializable {
    public TextArea name_text_area;
    public TextArea surname_text_area;
    public Label errorLabel;
    public Button add_button;
    public TextArea salary_text_area;
    public ComboBox<String> position_combobox;
    private OnNewEmploeeAddedListener mListener;
    private Stage mStage;
    private ObservableList<String> positions = FXCollections.observableArrayList("Department Manager", "Project Manager", "UX Designer", "UI Designer", "Backend Developer", "Frontend Developer", "Mobile Developer");

    public void setListener(OnNewEmploeeAddedListener listener) {
        mListener = listener;
    }

    public void setStage(Stage stage) {
        mStage = stage;
    }

    public void OnAddButtonClick(ActionEvent actionEvent) {
        boolean isValid = validate();
        if(isValid) {
            String name = name_text_area.textProperty().getValue();
            String surname = surname_text_area.textProperty().getValue();
            int positionIndex = positions.indexOf(position_combobox.getValue());
            int salary = Integer.parseInt(salary_text_area.getText());
            Employee employee = null;
            switch(positionIndex) {
                case 0: {
                    employee = new DepartmentManager(RepositoryProxy.getNewId(), name, surname, salary);
                    break;
                }
                case 1: {
                    employee = new ProjectManager(RepositoryProxy.getNewId(), name, surname, salary);
                    break;
                }
                case 2: {
                    employee = new UXDesigner(RepositoryProxy.getNewId(), name, surname, salary);
                    break;
                }
                case 3: {
                    employee = new UIDesigner(RepositoryProxy.getNewId(), name, surname, salary);
                    break;
                }
                case 4: {
                    employee = new BackendDeveloper(RepositoryProxy.getNewId(), name, surname, salary);
                    break;
                }
                case 5: {
                    employee = new FrontendDeveloper(RepositoryProxy.getNewId(), name, surname, salary);
                    break;
                }
                case 6: {
                    employee = new MobileDeveloper(RepositoryProxy.getNewId(), name, surname, salary);
                    break;
                }
            }

            mListener.onEmployeeAdded(employee);

            if(mStage != null) {
                mStage.hide();
            }
        }
    }

    private boolean validate() {
        boolean isValid = true;

//        short year = getYear();
//        if(year < 2000 || year > 2020) {
//            isValid = false;
//            showError("Year cannot be less than 2000 or grater than 2020");
//        }
//        if(surname_text_area.textProperty().getValue().equals("")) {
//            isValid = false;
//            showError("Surname field cannot be empty.");
//        }
//        if(phone_text_area.textProperty().getValue().equals("")) {
//            isValid = false;
//            showError("Phone field cannot be empty.");
//        }
//        if(year_text_area.textProperty().getValue().equals("")) {
//            isValid = false;
//            showError("Year field cannot be empty.");
//        }

        return isValid;
    }

//    private short getYear() {
//        short year = -1;
//        try {
//            if(!year_text_area.textProperty().getValue().isEmpty()) {
//                year = Short.parseShort(year_text_area.textProperty().getValue());
//            }
//        }
//        catch(NumberFormatException e) {
//            e.printStackTrace();
//        }
//        return year;
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        surname_text_area.setTextFormatter(new LettersTextFormatter());
        position_combobox.setItems(positions);
    }

    private void showError(String errorText) {
        errorLabel.setText(errorText);
        errorLabel.setVisible(true);
    }

    public void focusNextTextArea(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !event.isShiftDown() && !event.isControlDown()) {
            event.consume();
            Node node = (Node) event.getSource();
            KeyEvent newEvent
                    = new KeyEvent(event.getSource(),
                    event.getTarget(), event.getEventType(),
                    event.getCharacter(), event.getText(),
                    event.getCode(), event.isShiftDown(),
                    true, event.isAltDown(),
                    event.isMetaDown());

            node.fireEvent(newEvent);
        }
    }

    interface OnNewEmploeeAddedListener {
        void onEmployeeAdded(Employee employee);
    }
}