package com.handen.lab.oop1.shapes.other;

import com.handen.lab.oop1.shapes.Shape;
import com.handen.lab.utils.NumbersTextFormatter;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Line extends Shape {
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public Line() {

    }

    private Label label1 = new Label("Enter x1:");
    TextField textField1 = new TextField();

    private Label label2 = new Label("Enter y1:");
    TextField textField2 = new TextField();

    private Label label3 = new Label("Enter x2:");
    TextField textField3 = new TextField();

    private Label label4 = new Label("Enter y2:");
    TextField textField4 = new TextField();

    {
        textField1.setTextFormatter(new NumbersTextFormatter(100));
        textField2.setTextFormatter(new NumbersTextFormatter(100));
        textField3.setTextFormatter(new NumbersTextFormatter(100));
        textField4.setTextFormatter(new NumbersTextFormatter(100));
    }

    @Override
    public void draw(GraphicsContext context) {
        context.strokeLine(Integer.parseInt(textField1.getText()),
                Integer.parseInt(textField2.getText()),
                Integer.parseInt(textField3.getText()),
                Integer.parseInt(textField4.getText()));
    }

    @Override
    public void fillOptionsContanier(VBox container) {
        container.getChildren().add(label1);
        container.getChildren().add(textField1);

        container.getChildren().add(label2);
        container.getChildren().add(textField2);

        container.getChildren().add(label3);
        container.getChildren().add(textField3);

        container.getChildren().add(label4);
        container.getChildren().add(textField4);
    }

    @Override
    public String toString() {
        return "Line";
    }
}