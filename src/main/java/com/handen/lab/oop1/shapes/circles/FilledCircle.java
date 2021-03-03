package com.handen.lab.oop1.shapes.circles;

import javafx.scene.canvas.GraphicsContext;

public class FilledCircle extends Circle {

    public FilledCircle(int x, int y, int radius) {
        super(x, y, radius);
    }

    @Override
    public void draw(GraphicsContext context) {
        context.fillOval(x, y, radius, radius);
    }
}