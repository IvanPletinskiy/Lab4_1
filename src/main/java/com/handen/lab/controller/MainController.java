package com.handen.lab.controller;

import com.handen.lab.oop1.shapes.Shape;
import com.handen.lab.oop1.shapes.ShapesList;
import com.handen.lab.oop1.shapes.circles.Circle;
import com.handen.lab.oop1.shapes.circles.FilledCircle;
import com.handen.lab.oop1.shapes.other.Line;
import com.handen.lab.oop1.shapes.quardangles.Parallelogram;
import com.handen.lab.oop1.shapes.quardangles.Rectangle;
import com.handen.lab.oop1.shapes.quardangles.Square;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MainController implements Initializable {

    public Canvas canvas;
    public ComboBox<Shape> combobox;
    public VBox container;
    public Button drawButton;

    static final Line line = new Line();

    ObservableList<Shape> observableList = FXCollections.observableArrayList(line);

    static final Rectangle rectangle = new Rectangle(400, 100, 200, 100);
    static final Square square = new Square(700, 100, 100);
    static final Parallelogram parallelogram = new Parallelogram(100, 300, 200, 100, 0.8f);
    static final Circle circle = new Circle(400, 300, 100);
    static final FilledCircle filledCircle = new FilledCircle(700, 300, 100);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        combobox.setOnAction(actionEvent -> {
            System.out.println(combobox.getValue());
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

        for(int i = 0; i < shapesList.size(); i++) {
            Shape shape = shapesList.get(i);
            shape.draw(gc);
        }

        combobox.getValue().fillOptionsContanier(container);
    }

    public void OnDrawButtonClicked(MouseEvent mouseEvent) {
        combobox.getValue().draw(canvas.getGraphicsContext2D());
    }
}