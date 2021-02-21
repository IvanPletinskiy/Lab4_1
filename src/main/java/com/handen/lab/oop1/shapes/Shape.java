package com.handen.lab.oop1.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;

public abstract class Shape {
    public Shape() {

    }


    public abstract void draw(GraphicsContext context);
    public abstract void fillOptionsContanier(VBox container);
}