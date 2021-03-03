package com.handen.lab.oop1.shapes.other;

import com.handen.lab.oop1.shapes.Shape;

import javafx.scene.canvas.GraphicsContext;

public class Line extends Shape {
    private final int x2;
    private final int y2;

    public Line(int x, int y, int x2, int y2) {
        super(x, y);
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(GraphicsContext context) {
        context.strokeLine(x, y, x2, y2);
    }
}