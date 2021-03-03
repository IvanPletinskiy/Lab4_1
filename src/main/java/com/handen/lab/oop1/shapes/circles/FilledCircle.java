package com.handen.lab.oop1.shapes.circles;

import javafx.scene.canvas.GraphicsContext;

public class FilledCircle extends Circle {

    @Override
    public void draw(GraphicsContext context) {
        context.fillOval(Integer.parseInt(xTextField.getText()),
                Integer.parseInt(yTextField.getText()),
                Integer.parseInt(radiusTextField.getText()),
                Integer.parseInt(radiusTextField.getText()));
    }

    @Override
    public String toString() {
        return "FilledCircle";
    }
}