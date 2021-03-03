package com.handen.lab.oop1.shapes.quardangles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Parallelogram extends Quadrangle {
    TextField widthTextField;
    TextField heightTextField;
    TextField offsetTextField;

    public Parallelogram() {

    }

    @Override
    public void draw(GraphicsContext context) {
        int x = Integer.parseInt(xTextField.getText());
        int y = Integer.parseInt(yTextField.getText());
        int width = Integer.parseInt(widthTextField.getText());
        int height = Integer.parseInt(heightTextField.getText());
        float offset = Float.parseFloat(offsetTextField.getText()) / 100;

        int startX = x + width / 5;
        //counter clockwise
        context.strokeLine(startX, y, x, y + height);
        context.strokeLine(x, y + height, x + width * offset, y + height);
        context.strokeLine(x + width, y, x + width * offset, y + height);
        context.strokeLine(x + width, y, startX, y);
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

        Label offsetLabel = (Label) container.getChildren().get(8);
        offsetLabel.setText("Enter % offset:");
        offsetLabel.setVisible(true);
        offsetTextField = (TextField) container.getChildren().get(9);
        offsetTextField.setVisible(true);
    }

    @Override
    public String toString() {
        return "Parallelogram";
    }
}
