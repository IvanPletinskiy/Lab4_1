package com.handen.lab.oop1.shapes.quardangles;

import javafx.scene.canvas.GraphicsContext;

public class Parallelogram extends Quadrangle {
    private int height, width;
    private float offset;

    public Parallelogram(int x, int y, int width, int height, float offset) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.offset = offset;
    }

    @Override
    public void draw(GraphicsContext context) {
        int startX = x + width / 5;
        //counter clockwise
        context.strokeLine(startX, y, x, y + height);
        context.strokeLine(x, y + height , x + width * offset, y + height);
        context.strokeLine(x + width, y, x + width * offset, y + height);
        context.strokeLine(x + width, y, startX, y);
    }
}
