package com.handen.lab.oop1.shapes.other;

import com.handen.lab.oop1.shapes.Shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Line extends Shape {
    public Line() {

    }

    TextField x2TextField;
    TextField y2TextField;

    @Override
    public void draw(GraphicsContext context) {
        context.strokeLine(Integer.parseInt(xTextField.getText()),
                Integer.parseInt(yTextField.getText()),
                Integer.parseInt(x2TextField.getText()),
                Integer.parseInt(y2TextField.getText()));
    }

    @Override
    public void onSetupInputViews(VBox container) {
        Label x2Label = (Label) container.getChildren().get(4);
        x2Label.setText("Enter x2:");
        x2Label.setVisible(true);
        x2TextField = (TextField) container.getChildren().get(5);
        x2TextField.setVisible(true);

        Label y2Label = (Label) container.getChildren().get(6);
        y2Label.setText("Enter y2:");
        y2Label.setVisible(true);
        y2TextField = (TextField) container.getChildren().get(7);
        y2TextField.setVisible(true);
    }

    @Override
    public String toString() {
        return "Line";
    }
}