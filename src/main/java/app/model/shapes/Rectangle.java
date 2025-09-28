/**
 * Rectangle shape implementation
 */

package app.model.shapes;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.io.IOException;
import java.util.logging.*;

public class Rectangle extends Shape {
    private static final Logger logger = Logger.getLogger(Rectangle.class.getName());
    private double size;

    static {
        try {
            FileHandler fh = new FileHandler("logs/rectangle.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger", e);
        }
    }

    public Rectangle(Color color, Color colorStroke, double x, double y, double size) {
        super(color, colorStroke, x, y);
        this.size = size;
        logger.log(Level.FINE, String.format(
                "Rectangle created at (%.1f,%.1f) size %.1f", x, y, size));
    }

    @Override
    public double area() {
        double area = size * size;
        logger.log(Level.FINEST, "Area calculated: " + area);
        return area;
    }

    @Override
    public void draw(GraphicsContext gr) {
        logger.log(Level.FINER, "Drawing rectangle on canvas");
        gr.setStroke(colorStroke);
        gr.fillRect(x - size / 2, y - size / 2, size, size);
        gr.strokeRect(x - size / 2, y - size / 2, size, size);
    }

    @Override
    public void draw(Pane pane, Paint paint) {
        logger.log(Level.FINER, "Drawing rectangle on pane");
        javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(
                x - size / 2,
                y - size / 2,
                size,
                size
        );
        rect.setFill(paint);
        rect.setStroke(colorStroke);

        FadeTransition ft = new FadeTransition(Duration.millis(1000), rect);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();

        pane.getChildren().add(rect);
    }

    @Override
    public String toString() {
        return "Прямоугольник";
    }

    @Override
    public double[] getSize() {
        return new double[]{size, size};
    }

    @Override
    public void setSize(double size) {
        logger.log(Level.FINE, "Setting rectangle size to: " + size);
        this.size = size;
    }

    @Override
    public boolean contains(double clickX, double clickY) {
        boolean contains = clickX >= x - size / 2 &&
                clickX <= x + size / 2 &&
                clickY >= y - size / 2 &&
                clickY <= y + size / 2;
        logger.log(Level.FINEST, String.format(
                "Point (%.1f,%.1f) %s rectangle",
                clickX, clickY, contains ? "inside" : "outside"));
        return contains;
    }
}