package com.handen.lab.oop1.shapes.quardangles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Square extends Rectangle {
    TextField sideTextField;

    public Square() {

    }

    @Override
    public void draw(GraphicsContext context) {
        context.fillRect(Integer.parseInt(xTextField.getText()),
                Integer.parseInt(yTextField.getText()),
                Integer.parseInt(sideTextField.getText()),
                Integer.parseInt(sideTextField.getText()));
    }

    @Override
    public void onSetupInputViews(VBox container) {
        Label sideLabel = (Label) container.getChildren().get(4);
        sideLabel.setText("Enter side:");
        sideLabel.setVisible(true);
        sideTextField = (TextField) container.getChildren().get(5);
        sideTextField.setVisible(true);
    }

    @Override
    public String toString() {
        return "Square";
    }
}
