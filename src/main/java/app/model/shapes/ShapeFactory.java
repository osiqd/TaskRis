/**
 * Factory for creating shape objects
 */

package app.model.shapes;

import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.*;

public class ShapeFactory {
    private static final Logger logger = Logger.getLogger(ShapeFactory.class.getName());
    private final HashMap<Integer, Shape> shapes;

    static {
        try {
            FileHandler fh = new FileHandler("logs/shape_factory.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger", e);
        }
    }

    public ShapeFactory() {
        logger.log(Level.INFO, "Creating ShapeFactory");
        shapes = new HashMap<>();
        try {
            shapes.put(0, new Circle(Color.BLACK, Color.BLACK, 5, 5, 15));
            shapes.put(1, new Straight(Color.BLACK, Color.BLACK, 5, 5, 2));
            shapes.put(2, new Angle(Color.BLACK, Color.BLACK, 5, 5, 2));
            shapes.put(3, new Triangle(Color.BLACK, Color.BLACK, 5, 5, 2));
            shapes.put(4, new Rectangle(Color.BLACK, Color.BLACK, 5, 5, 15));
            shapes.put(5, new Pentagon(Color.BLACK, Color.BLACK, 5, 5, 2));
            logger.log(Level.INFO, "ShapeFactory initialized with 6 shapes");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize ShapeFactory", e);
        }
    }

    public Shape createShape(int index) {
        Shape shape = shapes.get(index);
        if (shape == null) {
            logger.log(Level.WARNING, "Invalid shape index requested: " + index);
        } else {
            logger.log(Level.FINE, "Created shape of type: " + shape.toString());
        }
        return shape;
    }
}