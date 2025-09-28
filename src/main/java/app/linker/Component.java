/**
 * Component class for composite pattern
 */

package app.linker;

import app.model.Decorate;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.logging.*;

public class Component {
    private static final Logger logger = Logger.getLogger(Component.class.getName());

    protected Decorate decorate;
    protected Color colorStroke;
    protected double[] xy;

    static {
        try {
            FileHandler fh = new FileHandler("logs/component.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger", e);
        }
    }

    public Component(Decorate decor) {
        this.decorate = decor;
        this.colorStroke = decorate.getShape().getColorStroke();
        logger.log(Level.FINE, "Component created for shape: " +
                decorate.getShape().toString());
    }

    public Decorate getDecorate() {
        logger.log(Level.FINEST, "Getting decorated shape");
        return decorate;
    }
}