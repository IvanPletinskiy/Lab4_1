package com.handen.lab.oop1.shapes.quardangles;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Quadrangle {
    private final int width;
    private final int height;

    public Rectangle(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext context) {
        context.fillRect(x, y, width, height);
    }
}