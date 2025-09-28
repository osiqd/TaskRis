/**
 * Pentagon shape implementation
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

public class Pentagon extends Shape {
    private static final Logger logger = Logger.getLogger(Pentagon.class.getName());
    private double size;
    private double[] xPoints;
    private double[] yPoints;

    static {
        try {
            FileHandler fh = new FileHandler("logs/pentagon.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger", e);
        }
    }

    public Pentagon(Color color, Color colorStroke, double x, double y, double size) {
        super(color, colorStroke, x, y);
        this.size = size;
        logger.log(Level.FINE, String.format(
                "Pentagon created at (%.1f,%.1f) size %.1f", x, y, size));
    }

    @Override
    public void draw(GraphicsContext gr) {
        logger.log(Level.FINER, "Drawing pentagon on canvas");
        gr.setStroke(colorStroke);
        gr.setLineWidth(2);

        int sides = 5;
        xPoints = new double[sides];
        yPoints = new double[sides];
        double angleStep = 2 * Math.PI / sides;

        for (int i = 0; i < sides; i++) {
            double angle = angleStep * i - Math.PI / 2;
            xPoints[i] = x + size * Math.cos(angle);
            yPoints[i] = y + size * Math.sin(angle);
        }

        gr.fillPolygon(xPoints, yPoints, sides);
        gr.strokePolygon(xPoints, yPoints, sides);
    }

    @Override
    public void draw(Pane pane, Paint paint) {
        logger.log(Level.FINER, "Drawing pentagon on pane");
        Polygon polygon = new Polygon();
        polygon.setFill(paint);
        polygon.setStroke(colorStroke);
        polygon.setStrokeWidth(2);

        int sides = 5;
        xPoints = new double[sides];
        yPoints = new double[sides];
        double angleStep = 2 * Math.PI / sides;

        for (int i = 0; i < sides; i++) {
            double angle = angleStep * i - Math.PI / 2;
            xPoints[i] = x + size * Math.cos(angle);
            yPoints[i] = y + size * Math.sin(angle);
        }

        for (int i = 0; i < sides; i++) {
            polygon.getPoints().addAll(xPoints[i], yPoints[i]);
        }

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
        double coefficient = 1.0 / 4.0 * Math.sqrt(5 * (5 + 2 * Math.sqrt(5)));
        double area = coefficient * size * size;
        logger.log(Level.FINEST, "Area calculated: " + area);
        return area;
    }

    @Override
    public String toString() {
        return "Пятиугольник";
    }

    @Override
    public double[] getSize() {
        return new double[]{xPoints[1] - xPoints[4], yPoints[2] - yPoints[0]};
    }

    @Override
    public void setSize(double size) {
        logger.log(Level.FINE, "Setting pentagon size to: " + size);
        this.size = size;
    }

    @Override
    public boolean contains(double clickX, double clickY) {
        int intersectCount = 0;
        for (int i = 0; i < xPoints.length; i++) {
            double x1 = xPoints[i];
            double y1 = yPoints[i];
            double x2 = xPoints[(i + 1) % xPoints.length];
            double y2 = yPoints[(i + 1) % yPoints.length];

            if ((clickY > Math.min(y1, y2)) &&
                    (clickY <= Math.max(y1, y2)) &&
                    (clickX <= Math.max(x1, x2))) {
                double xIntersect = (clickY - y1) * (x2 - x1) / (y2 - y1) + x1;
                if (y1 == y2 || clickX <= xIntersect) {
                    intersectCount++;
                }
            }
        }
        boolean contains = intersectCount % 2 != 0;
        logger.log(Level.FINEST, String.format(
                "Point (%.1f,%.1f) %s pentagon",
                clickX, clickY, contains ? "inside" : "outside"));
        return contains;
    }
}