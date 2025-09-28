package app.decorator;

import app.linker.Composite;
import app.model.Decorate;
import app.model.Momento;
import app.model.shapes.Shape;
import app.model.shapes.ShapeFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void deleteSelectedShape() {
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape circle = shapeFactory.createShape(0);
        Shape straight = shapeFactory.createShape(1);
        Shape angle = shapeFactory.createShape(2);
        Shape triangle = shapeFactory.createShape(3);
        Shape rectangle = shapeFactory.createShape(4);
        Shape pentagon = shapeFactory.createShape(5);
        Decorate circleDecorate = new Decorate(circle, null,null);
        Decorate straightDecorate = new Decorate(straight, null,null);
        Decorate angleDecorate = new Decorate(angle, null,null);
        Decorate triangleDecorate = new Decorate(triangle, null,null);
        Decorate rectangleDecorate = new Decorate(rectangle, null,null);
        Decorate pentagonDecorate = new Decorate(pentagon, null,null);

        Momento momento = new Momento();
        momento.push(circleDecorate);
        momento.push(straightDecorate);
        momento.push(angleDecorate);
        momento.push(triangleDecorate);
        momento.push(rectangleDecorate);
        momento.push(pentagonDecorate);

        Composite composite = new Composite();
        composite.select(circleDecorate, null);
        composite.select(straightDecorate, null);
        composite.select(angleDecorate, null);
    }
}