/**
 * Angle shape implementation
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

public class Angle extends Shape {
    private static final Logger logger = Logger.getLogger(Angle.class.getName());
    private double size;

    static {
        try {
            FileHandler fh = new FileHandler("logs/angle.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger", e);
        }
    }

    public Angle(Color color, Color colorStroke, double x, double y, double size) {
        super(color, colorStroke, x, y);
        this.size = size;
        logger.log(Level.FINE, String.format(
                "Angle created at (%.1f,%.1f) size %.1f", x, y, size));
    }

    @Override
    public double area() {
        double area = (x * size - x) + (y * size - y);
        logger.log(Level.FINEST, "Area calculated: " + area);
        return area;
    }

    @Override
    public void draw(GraphicsContext gr) {
        logger.log(Level.FINER, "Drawing angle on canvas");
        gr.setStroke(colorStroke);
        gr.setLineWidth(2);
        gr.strokeLine(x - (size * 25), y + (size * 25), x + size * 25, y + (size * 25));
        gr.strokeLine(x - (size * 25), y - (size * 25), x - (size * 25), y + (size * 25));
    }

    @Override
    public void draw(Pane pane, Paint paint) {
        logger.log(Level.FINER, "Drawing angle on pane");
        Line line1 = new Line(x - (size * 25), y + (size * 25), x + size * 25, y + (size * 25));
        Line line2 = new Line(x - (size * 25), y - (size * 25), x - (size * 25), y + (size * 25));

        line1.setStrokeWidth(2);
        line1.setStroke(paint);
        line2.setStrokeWidth(2);
        line2.setStroke(paint);

        FadeTransition ft = new FadeTransition(Duration.millis(1000), line1);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();

        ft = new FadeTransition(Duration.millis(1000), line2);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();

        pane.getChildren().addAll(line1, line2);
    }

    @Override
    public String toString() {
        return "Угол";
    }

    @Override
    public double[] getSize() {
        return new double[]{size * 50, size * 50};
    }

    @Override
    public void setSize(double size) {
        logger.log(Level.FINE, "Setting angle size to: " + size);
        this.size = size;
    }

    @Override
    public boolean contains(double clickX, double clickY) {
        double tolerance = 2.0;
        boolean contains = (clickX >= x - size * 25 - tolerance &&
                clickX <= x + size * 25 + tolerance &&
                Math.abs(clickY - (y + size * 25)) <= tolerance) ||
                (clickY >= y - size * 25 - tolerance &&
                        clickY <= y + size * 25 + tolerance &&
                        Math.abs(clickX - (x - size * 25)) <= tolerance);
        logger.log(Level.FINEST, String.format(
                "Point (%.1f,%.1f) %s angle",
                clickX, clickY, contains ? "inside" : "outside"));
        return contains;
    }
}