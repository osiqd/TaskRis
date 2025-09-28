/**
 * Абстрактный базовый класс для всех фигур.
 */

package app.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class Shape implements Cloneable {
    protected Color color;
    protected Color colorStroke;
    protected double x;
    protected double y;

    public Shape(Color color, Color colorStroke, double x, double y) {
        this.color = color;
        this.colorStroke = colorStroke;
        this.x = x;
        this.y = y;
    }

    public abstract double area();
    public abstract void draw(GraphicsContext gr);
    public abstract void draw(Pane pane, Paint paint);
    public abstract double[] getSize();
    public abstract void setSize(double size);
    public abstract boolean contains(double clickX, double clickY);

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColorStroke(Color colorStroke) {
        this.colorStroke = colorStroke;
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Color getColorStroke() {
        return colorStroke;
    }
}