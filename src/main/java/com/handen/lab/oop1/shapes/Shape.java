package com.handen.lab.oop1.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public abstract class Shape {
    protected TextField xTextField;
    protected TextField yTextField;

    public Shape() {

    }

    public abstract void draw(GraphicsContext context);

    public void setupInputViews(VBox container) {
        Label xLabel = (Label) container.getChildren().get(0);
        xLabel.setText("Enter x:");
        xLabel.setVisible(true);
        xTextField = (TextField) container.getChildren().get(1);
        xTextField.setVisible(true);

        Label yLabel = (Label) container.getChildren().get(2);
        yLabel.setText("Enter y:");
        yLabel.setVisible(true);
        yTextField = (TextField) container.getChildren().get(3);
        yTextField.setVisible(true);

        onSetupInputViews(container);
    }
    public abstract void onSetupInputViews(VBox container);
}