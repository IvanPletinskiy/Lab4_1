package com.handen.lab.oop1.shapes.quardangles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Rectangle extends Quadrangle {
    TextField widthTextField;
    TextField heightTextField;

    public Rectangle() {

    }

    @Override
    public void draw(GraphicsContext context) {
        context.fillRect(Integer.parseInt(xTextField.getText()),
                Integer.parseInt(yTextField.getText()),
                Integer.parseInt(widthTextField.getText()),
                Integer.parseInt(heightTextField.getText()));
    }

    @Override
    public void onSetupInputViews(VBox container) {
        Label widthLabel = (Label) container.getChildren().get(4);
        widthLabel.setText("Enter width:");
        widthLabel.setVisible(true);
        widthTextField = (TextField) container.getChildren().get(5);
        widthTextField.setVisible(true);

        Label heightLabel = (Label) container.getChildren().get(6);
        heightLabel.setText("Enter height:");
        heightLabel.setVisible(true);
        heightTextField = (TextField) container.getChildren().get(7);
        heightTextField.setVisible(true);
    }

    @Override
    public String toString() {
        return "Rectangle";
    }
}