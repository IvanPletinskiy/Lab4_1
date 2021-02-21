package com.handen.lab.oop1.shapes;

import java.util.ArrayList;
import java.util.List;

class ShapesList {
    private List<Shape> shapes = new ArrayList<>();

    public void addShape(Shape shape) {
        shapes.add(shape);
    }
}
