package com.handen.lab.oop1.shapes.quardangles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Parallelogram extends Quadrangle {

    TextField textField1;
    TextField textField2;
    TextField textField3;
    TextField textField4;
    TextField textField5;

    public Parallelogram() {

    }

    @Override
    public void draw(GraphicsContext context) {
        int x1 = Integer.parseInt(textField1.getText());
        int y1 = Integer.parseInt(textField2.getText());
        int width = Integer.parseInt(textField3.getText());
        int height = Integer.parseInt(textField4.getText());
        float offset = Float.parseFloat(textField5.getText()) / 100;

        int startX = x1 + width / 5;
        //counter clockwise
        context.strokeLine(startX, y1, x1, y1 + height);
        context.strokeLine(x1, y1 + height, x1 + width * offset, y1 + height);
        context.strokeLine(x1 + width, y1, x1 + width * offset, y1 + height);
        context.strokeLine(x1 + width, y1, startX, y1);
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
        label3.setText("Enter width:");
        label3.setVisible(true);
        textField3 = (TextField) container.getChildren().get(5);
        textField3.setVisible(true);

        Label label4 = (Label) container.getChildren().get(6);
        label4.setText("Enter height:");
        label4.setVisible(true);
        textField4 = (TextField) container.getChildren().get(7);
        textField4.setVisible(true);

        Label label5 = (Label) container.getChildren().get(8);
        label5.setText("Enter % offset:");
        label5.setVisible(true);
        textField5 = (TextField) container.getChildren().get(9);
        textField5.setVisible(true);
    }

    @Override
    public String toString() {
        return "Parallelogram";
    }
}
