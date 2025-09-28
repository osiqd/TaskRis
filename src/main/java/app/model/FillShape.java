/**
 * Fill shape utility class
 */

package app.model;

import javafx.scene.image.Image;
import javafx.scene.paint.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.*;

public class FillShape {
    private static final Logger logger = Logger.getLogger(FillShape.class.getName());
    private final HashMap<Integer, Paint> fills;

    static {
        try {
            FileHandler fh = new FileHandler("logs/fill_shape.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger", e);
        }
    }

    public FillShape(Color color, Color color1, double x, double y) {
        logger.log(Level.INFO, "Creating FillShape");
        fills = new HashMap<>();

        fills.put(0, color);
        logger.log(Level.FINE, "Added solid color fill");

        fills.put(1, new LinearGradient(
                0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, color),
                new Stop(1, color1)));
        logger.log(Level.FINE, "Added linear gradient fill");

        fills.put(2, new RadialGradient(
                0, 0.5, x - 40, y, 150, false,
                CycleMethod.NO_CYCLE,
                new Stop(0, color),
                new Stop(0.5, color1)));
        logger.log(Level.FINE, "Added radial gradient fill");

        try {
            fills.put(3, new ImagePattern(new Image("file:src/main/resources/app/img1.jpg")));
            logger.log(Level.FINE, "Added image pattern fill");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to load image pattern", e);
        }
    }

    public Paint getFill(int index) {
        Paint fill = fills.get(index);
        if (fill == null) {
            logger.log(Level.WARNING, "Invalid fill index: " + index);
            return fills.get(0);
        }
        logger.log(Level.FINE, "Getting fill type: " + index);
        return fill;
    }
}