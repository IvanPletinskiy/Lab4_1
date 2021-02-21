package com.handen.lab.oop1.shapes;

import java.util.ArrayList;
import java.util.List;

public class ShapesList {
    private List<Shape> shapes = new ArrayList<>();

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public int size() {
        return shapes.size();
    }

    public Shape get(int index) {
        return shapes.get(index);
    }
}
