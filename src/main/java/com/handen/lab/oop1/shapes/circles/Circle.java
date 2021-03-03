package com.handen.lab.oop1.shapes.circles;

import com.handen.lab.oop1.shapes.Shape;

import javafx.scene.canvas.GraphicsContext;

public class Circle extends Shape {
    protected final int radius;

    public Circle(int x, int y, int radius) {
        super(x, y);
        this.radius = radius;
    }

    @Override
    public void draw(GraphicsContext context) {
        context.strokeOval(x, y, radius, radius);
    }
}
