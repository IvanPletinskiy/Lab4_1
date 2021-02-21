package com.handen.lab.oop1.shapes.quardangles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;

public class Parallelogram extends Quadrangle {
    private int x1, y1, height, width;
    private float offset;

    public Parallelogram(int x1, int y1, int width, int height, float offset) {
        this.x1 = x1;
        this.y1 = y1;
        this.width = width;
        this.height = height;
        this.offset = offset;
    }

    @Override
    public void draw(GraphicsContext context) {
        int startX = x1 + width / 5;
        //counter clockwise
        context.strokeLine(startX, y1, x1, y1 + height);
        context.strokeLine(x1, y1 + height , x1 + width * offset, y1 + height);
        context.strokeLine(x1 + width, y1, x1 + width * offset, y1 + height);
        context.strokeLine(x1 + width, y1, startX, y1);
    }

    @Override
    public void fillOptionsContanier(VBox container) {

    }
}
