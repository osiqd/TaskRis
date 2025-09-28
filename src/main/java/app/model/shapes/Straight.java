/**
 * Straight line implementation
 */

package app.model.shapes;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.io.IOException;
import java.util.logging.*;

public class Straight extends Shape {
    private static final Logger logger = Logger.getLogger(Straight.class.getName());
    private double size;

    static {
        try {
            FileHandler fh = new FileHandler("logs/straight.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger", e);
        }
    }

    public Straight(Color color, Color colorStroke, double x, double y, double size) {
        super(color, colorStroke, x, y);
        this.size = size;
        logger.log(Level.FINE, String.format(
                "Straight line created at (%.1f,%.1f) size %.1f", x, y, size));
    }

    @Override
    public double area() {
        return 0;
    }

    @Override
    public void draw(GraphicsContext gr) {
        logger.log(Level.FINER, "Drawing straight line on canvas");
        gr.setStroke(colorStroke);
        gr.setLineWidth(2);
        gr.strokePolygon(
                new double[]{x - size * 25, x + size * 25},
                new double[]{y, y},
                2);
    }

    @Override
    public void draw(Pane pane, Paint paint) {
        logger.log(Level.FINER, "Drawing straight line on pane");
        Line line = new Line(x - size * 25, y, x + size * 25, y);
        line.setStroke(colorStroke);
        line.setStrokeWidth(2);

        FadeTransition ft = new FadeTransition(Duration.millis(1000), line);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();

        pane.getChildren().add(line);
    }

    @Override
    public String toString() {
        return "Линия";
    }

    @Override
    public double[] getSize() {
        return new double[]{size * 50, 0};
    }

    @Override
    public void setSize(double size) {
        logger.log(Level.FINE, "Setting line size to: " + size);
        this.size = size;
    }

    @Override
    public boolean contains(double clickX, double clickY) {
        double tolerance = 2.0;
        boolean contains = clickX >= x - size * 25 - tolerance &&
                clickX <= x + size * 25 + tolerance &&
                Math.abs(clickY - y) <= tolerance;
        logger.log(Level.FINEST, String.format(
                "Point (%.1f,%.1f) %s line",
                clickX, clickY, contains ? "on" : "not on"));
        return contains;
    }
}