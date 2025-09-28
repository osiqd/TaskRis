/**
 * Stipple effect implementation
 */

package app.model.addons;

import app.model.Decorate;
import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
import java.util.logging.*;

public class Stipple implements Addon {
    private static final Logger logger = Logger.getLogger(Stipple.class.getName());
    protected Decorate decorate;

    static {
        try {
            FileHandler fh = new FileHandler("logs/stipple.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger", e);
        }
    }

    public Stipple(Decorate decorate) {
        this.decorate = decorate;
        logger.log(Level.FINE, "Stipple addon created");
    }

    public void draw(GraphicsContext gr) {
        logger.log(Level.FINER, "Drawing stipple effect");
        gr.setLineDashes(10, 60);
        gr.setLineWidth(5);
        decorate.getShape().draw(gr);
        gr.setLineDashes(null);
        gr.setLineWidth(1);
    }
}