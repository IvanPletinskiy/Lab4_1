package com.handen.lab.controller;

import com.handen.lab.App;
import com.handen.lab.IoDialogController;
import com.handen.lab.data.Employee;
import com.handen.lab.data.developer.Developer;
import com.handen.lab.model.RepositoryProxy;
import com.handen.lab.utils.LettersTextFormatter;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController implements Initializable {

    public AnchorPane container;
    private Stage stage;

    public Label errorLabel;

    private RepositoryProxy repository = RepositoryProxy.getInstance();
    private final ObservableList<Employee> items = repository.getItems();

    public TableView<Employee> table;

    public TextArea name_text_area;
    public TextArea surname_text_area;
    public TextArea salary_text_area;
    public TextArea mentor_text_area;
    public Label mentorLabel;

    public Button add_button;
    public Button save_button;
    public Button delete_button;
    public MenuItem menu_load;
    public MenuItem menu_save;

    private int selectedRow;

    private EmployeeChangedListener employeeChangedListener = () -> {
        Employee item = items.get(selectedRow);
        boolean isChanged = !name_text_area.getText().equals(item.name) || !surname_text_area.textProperty().getValue().equals(item.getSurname()) ||
                Integer.parseInt(salary_text_area.textProperty().getValue()) != item.salary;

        if(item instanceof Developer) {
            Developer developer = (Developer) item;

            if(developer.mentor != null) {
                if(!mentor_text_area.getText().equals(developer.mentor.surname)) {
                    isChanged = true;
                }
            } else {
                if(!mentor_text_area.getText().isBlank()) {
                    isChanged = true;
                }
            }
        }

        save_button.setVisible(isChanged);
    };

    @FXML
    public void OnAddButtonClick(ActionEvent actionEvent) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("add_dialog.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add record");
            stage.setScene(new Scene(root, 450, 300));
            AddDialogController controller = loader.getController();
            controller.setStage(stage);
            controller.setListener(employee -> {
                if(employee != null) {
                    addItem(employee);
                }
            });
            stage.show();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void addItem(Employee employee) {
        items.add(employee);
    }

    @FXML
    public void OnSaveButtonClick(ActionEvent actionEvent) {
        boolean isValid = validate();
        if(isValid) {
            Employee employee = items.get(selectedRow);
            employee.setName(name_text_area.getText());
            employee.setSurname(surname_text_area.getText());
            employee.setSalary(Integer.parseInt(salary_text_area.getText()));
            if(employee instanceof Developer) {
                ((Developer) employee).setMentor(repository.getMentorBySurname(mentor_text_area.getText()));
            }

            repository.saveEmployee(selectedRow, employee);
        }
    }

    private boolean validate() {
        boolean isValid = true;

        if(surname_text_area.textProperty().getValue().equals("")) {
            isValid = false;
            showError("Surname field cannot be empty.");
        }
        if(mentor_text_area.isVisible() && !mentor_text_area.getText().isBlank()) {
            if(repository.getMentorBySurname(surname_text_area.getText()) == null) {
                isValid = false;
                showError("Can't find mentor by given surname");
            }
        }

        return isValid;
    }

    private void showError(String errorText) {
        errorLabel.setText(errorText);
        errorLabel.setVisible(true);
    }

    public void OnDeleteButtonClick(ActionEvent actionEvent) {
        if(!items.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure want to delete this record?");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                items.remove(selectedRow);
            }
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindTextFormatters();
        bindTextListeners();
        initializeTable();

        save_button.setVisible(false);
    }

    private void initializeTable() {
        table.setEditable(false);
        table.setItems(items);
        table.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int newSelectedId = (int) newValue;
            mentorLabel.setVisible(false);
            mentor_text_area.setVisible(false);
            mentor_text_area.setText("");
            if((int) newValue != -1) {
                Employee employee = items.get(newSelectedId);
                updateTextAreas(employee);
                selectedRow = newSelectedId;
                save_button.setVisible(false);
                errorLabel.setVisible(false);
            }
            else {
                surname_text_area.setText("");
            }
        });

        table.getSelectionModel().clearAndSelect(0);
    }

    private void updateTextAreas(Employee employee) {
        name_text_area.setText(employee.getName());
        surname_text_area.setText(employee.getSurname());
        salary_text_area.setText(String.valueOf(employee.getSalary()));
        if(employee instanceof Developer) {
            mentorLabel.setVisible(true);
            mentor_text_area.setVisible(true);
            if(((Developer) employee).getMentor() == null) {
                mentor_text_area.setText("");
            } else {
                mentor_text_area.setText(((Developer) employee).getMentor().getSurname());
            }
        }
    }

    private void bindTextFormatters() {
        surname_text_area.setTextFormatter(new LettersTextFormatter());
    }

    private void bindTextListeners() {
        name_text_area.textProperty().addListener((observable, oldValue, newValue) -> {
            employeeChangedListener.onChanged();
        });
        surname_text_area.textProperty().addListener((observable, oldValue, newValue) -> {
            employeeChangedListener.onChanged();
        });
        salary_text_area.textProperty().addListener((observable, oldValue, newValue) -> {
            employeeChangedListener.onChanged();
        });
        mentor_text_area.textProperty().addListener((observable, oldValue, newValue) -> {
            employeeChangedListener.onChanged();
        });
    }

    public void OnMenuLoadClicked(ActionEvent actionEvent) {
        showIoDialog(IoDialogController.IOMode.LOAD);
    }

    public void OnMenuSaveClicked(ActionEvent actionEvent) {
        showIoDialog(IoDialogController.IOMode.SAVE);
    }

    private void showIoDialog(IoDialogController.IOMode mode) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("io_dialog.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Save/load");
            stage.setScene(new Scene(root, 600, 600));
            IoDialogController controller = loader.getController();
            controller.setIoMode(mode);
            controller.setStage(stage);
            stage.show();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
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

    interface EmployeeChangedListener {
        void onChanged();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}