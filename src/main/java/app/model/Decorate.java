/**
 * Decorator pattern implementation for shapes
 */

package app.model;

import app.model.addons.Addon;
import app.model.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.util.List;
import java.util.logging.*;

public class Decorate {
    private static final Logger logger = Logger.getLogger(Decorate.class.getName());

    protected Shape shape;
    protected Paint paint;
    protected Effect effect;
    protected List<Addon> addons;

    static {
        try {
            FileHandler fh = new FileHandler("logs/decorate.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger", e);
        }
    }

    public Decorate(Shape shape, Paint paint, Effect effect) {
        this.shape = shape;
        this.paint = paint;
        this.effect = effect;
        logger.log(Level.FINE, "Decorate created for shape: " + shape.toString());
    }

    public void setPaint(Paint paint) {
        logger.log(Level.FINER, "Setting paint for decorate");
        this.paint = paint;
    }

    public void draw(GraphicsContext gr) {
        logger.log(Level.FINER, "Drawing decorated shape");
        gr.setFill(paint);
        gr.setEffect(effect);
        shape.draw(gr);
        for (Addon a : addons) {
            a.draw(gr);
        }
    }

    public void draw(Pane pane) {
        logger.log(Level.FINER, "Drawing decorated shape on pane");
        shape.draw(pane, paint);
    }

    public void setAddons(List<Addon> addons) {
        logger.log(Level.FINE, "Setting " + addons.size() + " addons");
        this.addons = addons;
    }

    public Shape getShape() {
        return shape;
    }
}