package com.handen.lab.oop1.shapes.circles;

import javafx.scene.canvas.GraphicsContext;

public class FilledCircle extends Circle {

    public FilledCircle(int x1, int y1, int radius) {
        super(x1, y1, radius);
    }

    @Override
    public void draw(GraphicsContext context) {
        context.fillOval(x1, y1, radius, radius);
    }
}