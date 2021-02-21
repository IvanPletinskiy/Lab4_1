package com.handen.lab.controller;

import com.handen.lab.App;
import com.handen.lab.data.Record;
import com.handen.lab.model.Repository;
import com.handen.lab.oop1.shapes.Shape;
import com.handen.lab.oop1.shapes.ShapesList;
import com.handen.lab.oop1.shapes.circles.Circle;
import com.handen.lab.oop1.shapes.circles.FilledCircle;
import com.handen.lab.oop1.shapes.other.Line;
import com.handen.lab.oop1.shapes.quardangles.Parallelogram;
import com.handen.lab.oop1.shapes.quardangles.Rectangle;
import com.handen.lab.oop1.shapes.quardangles.Square;
import com.handen.lab.utils.LettersTextFormatter;
import com.handen.lab.utils.NumbersTextFormatter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController implements Initializable {

    public AnchorPane container;
    public Canvas canvas;

    static final Line line = new Line(100, 100, 200, 200);
    static final Rectangle rectangle = new Rectangle(400, 100, 200, 100);
    static final Square square = new Square(700, 100, 100);
    static final Parallelogram parallelogram = new Parallelogram(100, 300, 200, 100, 0.8f);
    static final Circle circle = new Circle(400, 300, 100);
    static final Circle filledCircle = new FilledCircle(700, 300, 100);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ShapesList shapesList = new ShapesList();
        shapesList.addShape(line);
        shapesList.addShape(rectangle);
        shapesList.addShape(square);
        shapesList.addShape(parallelogram);
        shapesList.addShape(circle);
        shapesList.addShape(filledCircle);

        canvas.setHeight(1000);
        canvas.setWidth(1000);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

        for(int i = 0; i < shapesList.size(); i++) {
            Shape shape = shapesList.get(i);
            shape.draw(gc);
        }
    }
}