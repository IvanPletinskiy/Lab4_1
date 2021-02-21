package com.handen.lab.oop1.shapes.quardangles;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Quadrangle {

    private final int x1;
    private final int y1;
    private final int width;
    private final int height;

    public Rectangle(int x1, int y1, int width, int height) {
        this.x1 = x1;
        this.y1 = y1;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext context) {
        context.fillRect(x1, y1, width, height);
    }
}