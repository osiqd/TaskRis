package app.model;

import app.model.shapes.Shape;
import app.model.shapes.ShapeFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MomentoTest {

    @Test
    void getListShapes() {
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape circle = shapeFactory.createShape(0);
        Shape straight = shapeFactory.createShape(1);
        Shape angle = shapeFactory.createShape(2);
        Shape triangle = shapeFactory.createShape(3);
        Shape rectangle = shapeFactory.createShape(4);
        Shape pentagon = shapeFactory.createShape(5);

        ArrayList<Object> actual = new ArrayList<>();
        actual.add(circle);
        actual.add(straight);
        actual.add(angle);
        actual.add(triangle);
        actual.add(rectangle);
        actual.add(pentagon);
        Momento momento = new Momento();
        momento.push(circle);
        momento.push(straight);
        momento.push(angle);
        momento.push(triangle);
        momento.push(rectangle);
        momento.push(pentagon);
        ArrayList<Object> expected = momento.getListShapes();

        assertEquals(actual, expected);
    }

    @Test
    void getListShapes_NO_NULL() {
        Momento momento = new Momento();
        ArrayList<Object> expected = momento.getListShapes();
        assertNotNull(expected);
    }

    @Test
    void getSize() {
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape circle = shapeFactory.createShape(0);
        Shape straight = shapeFactory.createShape(1);
        Shape angle = shapeFactory.createShape(2);
        Shape triangle = shapeFactory.createShape(3);
        Shape rectangle = shapeFactory.createShape(4);
        Shape pentagon = shapeFactory.createShape(5);

        Momento momento = new Momento();
        momento.push(circle);
        momento.push(straight);
        momento.push(angle);
        momento.push(triangle);
        momento.push(rectangle);
        momento.push(pentagon);

        int actual = 6;

        int expected = momento.getSize();
        assertEquals(actual, expected);
    }
}