package com.handen.lab.oop1.shapes.circles;

import com.handen.lab.oop1.shapes.Shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Circle extends Shape {

    protected TextField radiusTextField;

    @Override
    public void draw(GraphicsContext context) {
        context.strokeOval(Integer.parseInt(xTextField.getText()),
                Integer.parseInt(yTextField.getText()),
                Integer.parseInt(radiusTextField.getText()),
                Integer.parseInt(radiusTextField.getText()));
    }

    @Override
    public void onSetupInputViews(VBox container) {
        Label radiusLabel = (Label) container.getChildren().get(4);
        radiusLabel.setText("Enter radius:");
        radiusLabel.setVisible(true);
        radiusTextField = (TextField) container.getChildren().get(5);
        radiusTextField.setVisible(true);
    }

    @Override
    public String toString() {
        return "Circle";
    }
}
