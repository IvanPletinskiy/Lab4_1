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

    TextField textField1;
    TextField textField2;
    TextField textField3;
    TextField textField4;

    @Override
    public void draw(GraphicsContext context) {
        context.strokeLine(Integer.parseInt(textField1.getText()),
                Integer.parseInt(textField2.getText()),
                Integer.parseInt(textField3.getText()),
                Integer.parseInt(textField4.getText()));
    }

    @Override
    public void setupInputViews(VBox container) {
        Label label1 = (Label) container.getChildren().get(0);
        label1.setText("Enter x1:");
        label1.setVisible(true);
        textField1 = (TextField) container.getChildren().get(1);
        textField1.setVisible(true);

        Label label2 = (Label) container.getChildren().get(2);
        label2.setText("Enter y1:");
        label2.setVisible(true);
        textField2 = (TextField) container.getChildren().get(3);
        textField2.setVisible(true);

        Label label3 = (Label) container.getChildren().get(4);
        label3.setText("Enter x2:");
        label3.setVisible(true);
        textField3 = (TextField) container.getChildren().get(5);
        textField3.setVisible(true);

        Label label4 = (Label) container.getChildren().get(6);
        label4.setText("Enter y2:");
        label4.setVisible(true);
        textField4 = (TextField) container.getChildren().get(7);
        textField4.setVisible(true);
    }

    @Override
    public String toString() {
        return "Line";
    }
}