package com.handen.lab.oop1.shapes;

import javafx.scene.canvas.GraphicsContext;

public abstract class Shape {
    protected int x, y;

    public Shape(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void draw(GraphicsContext context);
}