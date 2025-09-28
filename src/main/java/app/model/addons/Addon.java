/**
 * Интерфейс для дополнительных эффектов фигур.
 */

package app.model.addons;

import javafx.scene.canvas.GraphicsContext;

public interface Addon {
    void draw(GraphicsContext gr);
}