/**
 * Triangle shape implementation
 */

package app.model.shapes;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.io.IOException;
import java.util.logging.*;

public class Triangle extends Shape {
    private static final Logger logger = Logger.getLogger(Triangle.class.getName());
    private double size;

    static {
        try {
            FileHandler fh = new FileHandler("logs/triangle.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger", e);
        }
    }

    public Triangle(Color color, Color colorStroke, double x, double y, double size) {
        super(color, colorStroke, x, y);
        this.size = size;
        logger.log(Level.FINE, String.format(
                "Triangle created at (%.1f,%.1f) size %.1f", x, y, size));
    }

    private double[] getPointsX() {
        return new double[]{
                x,
                x - size / 2,
                x + size / 2
        };
    }

    private double[] getPointsY() {
        return new double[]{
                y - size * Math.sqrt(3) / 4,
                y + size * Math.sqrt(3) / 4,
                y + size * Math.sqrt(3) / 4
        };
    }

    @Override
    public void draw(GraphicsContext gr) {
        logger.log(Level.FINER, "Drawing triangle on canvas");
        gr.setStroke(colorStroke);
        gr.setLineWidth(2);
        gr.fillPolygon(getPointsX(), getPointsY(), 3);
        gr.strokePolygon(getPointsX(), getPointsY(), 3);
    }

    @Override
    public void draw(Pane pane, Paint paint) {
        logger.log(Level.FINER, "Drawing triangle on pane");
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(
                x, y - size * Math.sqrt(3) / 4,
                x - size / 2, y + size * Math.sqrt(3) / 4,
                x + size / 2, y + size * Math.sqrt(3) / 4);
        polygon.setFill(paint);
        polygon.setStroke(colorStroke);
        polygon.setStrokeWidth(2);

        FadeTransition ft = new FadeTransition(Duration.millis(1000), polygon);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();

        pane.getChildren().add(polygon);
    }

    @Override
    public double area() {
        double area = size * size * Math.sqrt(3) / 4;
        logger.log(Level.FINEST, "Area calculated: " + area);
        return area;
    }

    @Override
    public String toString() {
        return "Треугольник";
    }

    @Override
    public double[] getSize() {
        return new double[]{size, size * Math.sqrt(3) / 2};
    }

    @Override
    public void setSize(double size) {
        logger.log(Level.FINE, "Setting triangle size to: " + size);
        this.size = size;
    }

    @Override
    public boolean contains(double clickX, double clickY) {
        double[] xPoints = getPointsX();
        double[] yPoints = getPointsY();

        double A = area(xPoints[0], yPoints[0], xPoints[1], yPoints[1], xPoints[2], yPoints[2]);
        double A1 = area(clickX, clickY, xPoints[1], yPoints[1], xPoints[2], yPoints[2]);
        double A2 = area(xPoints[0], yPoints[0], clickX, clickY, xPoints[2], yPoints[2]);
        double A3 = area(xPoints[0], yPoints[0], xPoints[1], yPoints[1], clickX, clickY);

        boolean contains = Math.abs(A - (A1 + A2 + A3)) < 0.0001;
        logger.log(Level.FINEST, String.format(
                "Point (%.1f,%.1f) %s triangle",
                clickX, clickY, contains ? "inside" : "outside"));
        return contains;
    }

    private double area(
            double x1, double y1,
            double x2, double y2,
            double x3, double y3) {
        return Math.abs(
                (x1 * (y2 - y3) +
                        x2 * (y3 - y1) +
                        x3 * (y1 - y2)) / 2.0);
    }
}