/**
 * Circle shape implementation
 */

package app.model.shapes;

import javafx.animation.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.io.IOException;
import java.util.logging.*;

public class Circle extends Shape {
    private static final Logger logger = Logger.getLogger(Circle.class.getName());
    protected double diameter;
    protected double radius;

    static {
        try {
            FileHandler fh = new FileHandler("logs/circle.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger", e);
        }
    }

    public Circle(Color color, Color colorStroke, double x, double y, double diameter) {
        super(color, colorStroke, x, y);
        this.diameter = diameter;
        this.radius = diameter / 2;
        logger.log(Level.FINE, String.format(
                "Circle created at (%.1f,%.1f) with diameter %.1f", x, y, diameter));
    }

    @Override
    public double area() {
        double area = Math.PI * radius * radius;
        logger.log(Level.FINEST, "Calculated area: " + area);
        return area;
    }

    @Override
    public void draw(Pane pane, Paint paint) {
        logger.log(Level.FINER, "Drawing circle on pane");
        javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(x, y, radius);
        circle.setStroke(colorStroke);
        circle.setFill(paint);

        FadeTransition ft = new FadeTransition(Duration.millis(1000), circle);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();

        pane.getChildren().add(circle);
    }

    @Override
    public void draw(GraphicsContext gr) {
        logger.log(Level.FINER, String.format(
                "Drawing circle on graphics context at (%.1f,%.1f) radius %.1f",
                x, y, radius));

        gr.setStroke(colorStroke);
        gr.fillOval(x - radius, y - radius, diameter, diameter);
        gr.strokeOval(x - radius, y - radius, diameter, diameter);
    }

    @Override
    public String toString() {
        return "Круг";
    }

    @Override
    public double[] getSize() {
        return new double[]{diameter, diameter};
    }

    @Override
    public void setSize(double size) {
        logger.log(Level.FINER, "Setting circle size to: " + size);
        radius = size / 2;
        diameter = size;
    }

    @Override
    public boolean contains(double clickX, double clickY) {
        double distanceSquared = Math.pow(clickX - x, 2) + Math.pow(clickY - y, 2);
        boolean contains = distanceSquared <= Math.pow(radius, 2);
        logger.log(Level.FINEST, String.format(
                "Point (%.1f,%.1f) %s circle",
                clickX, clickY, contains ? "inside" : "outside"));
        return contains;
    }
}