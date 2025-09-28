/**
 * Composite pattern implementation for shapes
 */

package app.linker;

import app.model.Decorate;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.*;

public class Composite {
    private static final Logger logger = Logger.getLogger(Composite.class.getName());
    private final ArrayList<Component> array = new ArrayList<>();

    static {
        try {
            FileHandler fh = new FileHandler("logs/composite.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger", e);
        }
    }

    public Composite() {
        logger.log(Level.INFO, "Composite created");
    }

    public void select(Decorate decorate, GraphicsContext gr) {
        logger.log(Level.FINE, "Selecting decorate object");
        for (Component comp : array) {
            if (comp.getDecorate().equals(decorate)) {
                comp.getDecorate().getShape().setColorStroke(comp.colorStroke);
                array.remove(comp);
                logger.log(Level.FINE, "Deselected decorate object");
                return;
            }
        }
        Component comp = new Component(decorate);
        comp.getDecorate().getShape().setColorStroke(Color.RED);
        array.add(comp);
        logger.log(Level.FINE, "Selected decorate object");
    }

    public void remove() {
        logger.log(Level.INFO, "Removing all components");
        for (Component comp : array) {
            comp.getDecorate().getShape().setColorStroke(comp.colorStroke);
        }
        array.clear();
    }

    public void saveCoord() {
        logger.log(Level.FINER, "Saving coordinates for all components");
        for (Component component : array) {
            component.xy = new double[]{
                    component.getDecorate().getShape().getX(),
                    component.getDecorate().getShape().getY()
            };
        }
    }

    public void changeXY(double x, double y) {
        logger.log(Level.FINER, String.format(
                "Changing coordinates by (%.1f,%.1f)", x, y));
        for (Component comp : array) {
            comp.getDecorate().getShape().setXY(
                    (x + comp.xy[0]),
                    (y + comp.xy[1]));
        }
    }

    public ArrayList<Decorate> getArray() {
        logger.log(Level.FINE, "Getting array of decorates");
        ArrayList<Decorate> result = new ArrayList<>();
        for (Component component : array) {
            result.add(component.getDecorate());
        }
        return result;
    }
}