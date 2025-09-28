/**
 * Memento pattern implementation for undo functionality
 */

package app.model;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.logging.*;

public class Momento {
    private static final Logger logger = Logger.getLogger(Momento.class.getName());
    private final Queue<Object> momentoList = new ArrayDeque<>();

    static {
        try {
            FileHandler fh = new FileHandler("logs/momento.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger", e);
        }
    }

    public Momento() {
        logger.log(Level.INFO, "Momento created");
    }

    public void push(Object state) {
        logger.log(Level.FINE, "Pushing state to momento");
        momentoList.add(state);
    }

    public void poll() {
        logger.log(Level.FINE, "Polling state from momento");
        momentoList.poll();
    }

    public void remove(Object state) {
        boolean removed = momentoList.remove(state);
        logger.log(Level.FINE, "Remove operation " + (removed ? "succeeded" : "failed"));
    }

    public int getSize() {
        int size = momentoList.size();
        logger.log(Level.FINER, "Getting momento size: " + size);
        return size;
    }

    public ArrayList<Object> getListShapes() {
        logger.log(Level.FINE, "Getting list of shapes");
        return new ArrayList<>(momentoList);
    }
}