package com.handen.lab.oop1.shapes.other;

import com.handen.lab.oop1.shapes.Shape;

import javafx.scene.canvas.GraphicsContext;

public class Line extends Shape {
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;

    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(GraphicsContext context) {
        context.strokeLine(x1, y1, x2, y2);
//        context.moveTo(x1, y1);
//        context.lineTo(x2, y2);
//        context.save();
    }
}