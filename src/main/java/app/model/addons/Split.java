/**
 * Split effect implementation
 */

package app.model.addons;

import app.model.Decorate;
import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
import java.util.logging.*;

public class Split implements Addon {
    private static final Logger logger = Logger.getLogger(Split.class.getName());
    protected Decorate decorate;

    static {
        try {
            FileHandler fh = new FileHandler("logs/split.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger", e);
        }
    }

    public Split(Decorate decorate) {
        this.decorate = decorate;
        logger.log(Level.FINE, "Split addon created");
    }

    public void draw(GraphicsContext gr) {
        logger.log(Level.FINER, "Drawing split effect");
        for (int i = 0; i < 4; i++) {
            double angle = Math.toRadians(90 * i);
            double endX = decorate.getShape().getX() +
                    decorate.getShape().getSize()[0] / 2 * Math.cos(angle);
            double endY = decorate.getShape().getY() +
                    decorate.getShape().getSize()[1] / 2 * Math.sin(angle);
            gr.strokeLine(
                    decorate.getShape().getX(),
                    decorate.getShape().getY(),
                    endX,
                    endY);
        }
    }
}