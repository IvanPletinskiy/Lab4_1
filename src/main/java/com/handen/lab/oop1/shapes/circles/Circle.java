package com.handen.lab.oop1.shapes.circles;

import com.handen.lab.oop1.shapes.Shape;

import javafx.scene.canvas.GraphicsContext;

public class Circle extends Shape {

    protected final int x1;
    protected final int y1;
    protected final int radius;

    public Circle(int x1, int y1, int radius) {
        this.x1 = x1;
        this.y1 = y1;
        this.radius = radius;
    }

    @Override
    public void draw(GraphicsContext context) {
        context.strokeOval(x1, y1, radius, radius);
    }
}
