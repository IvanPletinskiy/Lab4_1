package com.handen.lab.controller;

import com.handen.lab.oop1.shapes.Shape;
import com.handen.lab.oop1.shapes.ShapesList;
import com.handen.lab.oop1.shapes.circles.Circle;
import com.handen.lab.oop1.shapes.circles.FilledCircle;
import com.handen.lab.oop1.shapes.other.Line;
import com.handen.lab.oop1.shapes.quardangles.Parallelogram;
import com.handen.lab.oop1.shapes.quardangles.Rectangle;
import com.handen.lab.oop1.shapes.quardangles.Square;
import com.handen.lab.utils.NumbersTextFormatter;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MainController implements Initializable {

    public Canvas canvas;
    public ComboBox<Shape> combobox;
    public VBox container;
    public Button drawButton;

    static final Line line = new Line();
    static final Rectangle rectangle = new Rectangle();
    public TextField textField1;
    public TextField textField2;
    public TextField textField3;
    public TextField textField4;
    public TextField textField5;
    public TextField textField6;
    public Label errorLabel;

    private TextField[] textFields;

    ObservableList<Shape> observableList = FXCollections.observableArrayList(line, rectangle);
    static final Square square = new Square(700, 100, 100);
    static final Parallelogram parallelogram = new Parallelogram(100, 300, 200, 100, 0.8f);
    static final Circle circle = new Circle(400, 300, 100);
    static final FilledCircle filledCircle = new FilledCircle(700, 300, 100);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        combobox.setOnAction(actionEvent -> {
            System.out.println(combobox.getValue());
            updateInputView();
        });
        combobox.setItems(observableList);

        combobox.setValue(line);

        ShapesList shapesList = new ShapesList();
        shapesList.addShape(line);
        shapesList.addShape(rectangle);
        shapesList.addShape(square);
        shapesList.addShape(parallelogram);
        shapesList.addShape(circle);
        shapesList.addShape(filledCircle);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

        textFields = new TextField[]{textField1, textField2, textField3, textField4, textField5, textField6};
        Arrays.stream(textFields).forEach(textField -> {
            textField.setTextFormatter(new NumbersTextFormatter(100));
        });
        updateInputView();
    }

    private void updateInputView() {
        Arrays.stream(textFields).forEach(textField -> {
            textField.setText("");
        });
        container.getChildren().forEach(node -> {
            node.setVisible(false);
        });
        container.getChildren().get(container.getChildren().size() - 1);

        combobox.getValue().setupInputViews(container);
    }

    public void OnDrawButtonClicked(MouseEvent mouseEvent) {
        boolean isValid = validateInput();
        if(isValid) {
            combobox.getValue().draw(canvas.getGraphicsContext2D());
        }
    }

    private boolean validateInput() {
        boolean isInputValid = Arrays.stream(textFields).allMatch(textField -> !textField1.isVisible() || !textField1.getText().isBlank());
        errorLabel.setVisible(!isInputValid);
        if(!isInputValid) {
            errorLabel.setWrapText(true);
            errorLabel.setText("Verify your text fields. Some of them are empty");
        }
        return isInputValid;
    }
}