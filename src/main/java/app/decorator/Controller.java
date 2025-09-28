/**
 * Controller class for managing the application UI
 */

package app.decorator;

import app.linker.Composite;
import app.model.*;
import app.model.addons.Addon;
import app.model.addons.Split;
import app.model.addons.Stipple;
import app.model.shapes.Shape;
import app.model.shapes.ShapeFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.*;

public class Controller implements Initializable {
    private static final Logger logger = Logger.getLogger(Controller.class.getName());

    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private ColorPicker colorPicker1;
    @FXML
    private ColorPicker colorPickerStroke;
    @FXML
    private TextField fieldSize;
    @FXML
    private ChoiceBox<String> choiceFill;
    @FXML
    private ChoiceBox<EffectEnum> choiceEffect;
    @FXML
    private ChoiceBox<String> choiceWorkMode;
    @FXML
    private Label textLast;
    @FXML
    private ListView<Shape> listView;
    @FXML
    private HBox boxGradient;
    @FXML
    private Pane pane;
    @FXML
    private ToggleButton toggleSplit;
    @FXML
    private ToggleButton toggleStipple;

    private ObservableList<String> listFill;
    private ObservableList<String> listWorkMode;
    private final ArrayList<String> lastShape = new ArrayList<>();
    private Momento momento = new Momento();
    private final Composite composite = new Composite();
    private double startX;
    private double startY;

    static {
        try {
            FileHandler fh = new FileHandler("logs/controller.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger", e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.log(Level.INFO, "Initializing controller");

        try {
            ShapeFactory shapeFactory = new ShapeFactory();
            ObservableList<Shape> items = FXCollections.observableArrayList(
                    shapeFactory.createShape(0),
                    shapeFactory.createShape(1),
                    shapeFactory.createShape(2),
                    shapeFactory.createShape(3),
                    shapeFactory.createShape(4),
                    shapeFactory.createShape(5));

            listView.setItems(items);
            listFill = FXCollections.observableArrayList(
                    "Цвет", "Линейный градиент",
                    "Радиальный градиент", "Изображение");
            choiceFill.setItems(listFill);
            choiceFill.setValue(listFill.getFirst());

            ObservableList<EffectEnum> listEffect = FXCollections.observableArrayList(EffectEnum.values());
            choiceEffect.setItems(listEffect);
            choiceEffect.setValue(EffectEnum.NONE);

            listWorkMode = FXCollections.observableArrayList(
                    "Рисование", "Выделение", "Перемещение");
            choiceWorkMode.setItems(listWorkMode);
            choiceWorkMode.setValue("Рисование");

            choiceFill.getSelectionModel().selectedIndexProperty().addListener(
                    (_, _, t1) -> changeFill(t1.intValue()));
            choiceWorkMode.getSelectionModel().selectedIndexProperty().addListener(
                    (_, _, t1) -> setWorkMode(t1.intValue()));

            logger.log(Level.INFO, "Controller initialized successfully");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller initialization failed", e);
        }
    }

    public Effect setEffect() {
        Effect effect = new EffectShape().getEffect(choiceEffect.getValue());
        logger.log(Level.FINE, "Setting effect: " + choiceEffect.getValue());
        return effect;
    }

    public Paint setFill(double x, double y) {
        Paint fill = new FillShape(
                colorPicker.getValue(),
                colorPicker1.getValue(),
                x, y).getFill(choiceFill.getSelectionModel().getSelectedIndex());
        logger.log(Level.FINE, "Setting fill type: " + choiceFill.getValue());
        return fill;
    }

    public void changeFill(int num) {
        String choice = listFill.get(num);
        logger.log(Level.INFO, "Changing fill to: " + choice);
        switch (choice) {
            case "Color":
            case "Image":
                boxGradient.setVisible(false);
                break;
            default:
                boxGradient.setVisible(true);
        }
    }

    public void setWorkMode(int num) {
        String choice = listWorkMode.get(num);
        logger.log(Level.INFO, "Setting work mode to: " + choice);

        if (choice.equals("Рисование")) {
            composite.remove();
            clearBox();
            momentoDraw(canvas.getGraphicsContext2D());
            canvas.setOnMouseDragged(this::drawShape);
            canvas.setOnMousePressed(this::drawShape);
        } else if (choice.equals("Выделение")) {
            canvas.setOnMouseDragged(null);
            canvas.setOnMousePressed(this::selectShape);
        } else {
            canvas.setOnMouseDragged(this::dragMoveSelectedShape);
            canvas.setOnMousePressed(this::startMoveSelectedShape);
        }
    }

    @FXML
    protected void onClickClear() {
        logger.log(Level.INFO, "Clearing canvas");
        momento = new Momento();
        pane.getChildren().clear();
        clearBox();
        textLast.setText("Nothing drawn");
        lastShape.clear();
        canvas.toFront();
    }

    private void clearBox() {
        logger.log(Level.FINE, "Clearing drawing box");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(null);
        gc.setEffect(null);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        pane.getChildren().clear();
    }

    public void drawShape(MouseEvent mouseEvent) {
        logger.log(Level.FINE, "Drawing shape at X:" + mouseEvent.getX() + " Y:" + mouseEvent.getY());

        try {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            Shape shape = (Shape) listView.getSelectionModel().getSelectedItem().clone();

            shape.setColor(colorPicker.getValue());
            shape.setColorStroke(colorPickerStroke.getValue());
            shape.setXY(mouseEvent.getX(), mouseEvent.getY());

            if (!fieldSize.getText().isEmpty()) {
                try {
                    double size = Double.parseDouble(fieldSize.getText());
                    shape.setSize(size);
                } catch (NumberFormatException e) {
                    logger.log(Level.WARNING, "Invalid size value: " + fieldSize.getText());
                }
            }

            Decorate decorate = new Decorate(shape, setFill(mouseEvent.getX(), mouseEvent.getY()), setEffect());
            List<Addon> addons = new ArrayList<>();

            if (toggleStipple.isSelected()) {
                addons.add(new Stipple(decorate));
                logger.log(Level.FINER, "Added stipple effect");
            }
            if (toggleSplit.isSelected()) {
                addons.add(new Split(decorate));
                logger.log(Level.FINER, "Added split effect");
            }

            decorate.setAddons(addons);

            if (choiceEffect.getValue() == EffectEnum.FADE) {
                decorate.draw(pane);
            } else {
                decorate.draw(gc);
            }

            momento.push(decorate);
            lastShape.add(shape.toString());
            textLast.setText(lastShape.get(lastShape.size() - 1));
            canvas.toFront();

            logger.log(Level.FINE, "Drawn shape: " + shape.toString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error drawing shape", e);
        }
    }

    public void undoLast() {
        logger.log(Level.INFO, "Undoing last action");
        if (momento.getSize() > 1) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setEffect(null);
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            pane.getChildren().clear();
            lastShape.remove(lastShape.size() - 1);
            momento.poll();
            momentoDraw(gc);
            textLast.setText(lastShape.get(lastShape.size() - 1));
        } else {
            onClickClear();
            textLast.setText("Nothing drawn");
        }
        canvas.toFront();
    }

    private void momentoDraw(GraphicsContext gc) {
        logger.log(Level.FINE, "Redrawing from momento");
        for (Object item : momento.getListShapes()) {
            if (item instanceof Pane) {
                pane.getChildren().add((Pane) item);
            } else if (item instanceof Decorate) {
                ((Decorate) item).draw(gc);
            }
        }
    }

    public void selectShape(MouseEvent mouseEvent) {
        logger.log(Level.FINE, "Selecting shape at X:" + mouseEvent.getX() + " Y:" + mouseEvent.getY());
        Decorate decorate = null;
        for (Object item : momento.getListShapes()) {
            if (((Decorate) item).getShape().contains(mouseEvent.getX(), mouseEvent.getY())) {
                decorate = (Decorate) item;
                break;
            }
        }
        if (decorate != null) {
            composite.select(decorate, canvas.getGraphicsContext2D());
            momentoDraw(canvas.getGraphicsContext2D());
            logger.log(Level.INFO, "Selected shape: " + decorate.getShape().toString());
        }
    }

    public void startMoveSelectedShape(MouseEvent mouseEvent) {
        startX = mouseEvent.getX();
        startY = mouseEvent.getY();
        composite.saveCoord();
        logger.log(Level.FINE, "Starting move at X:" + startX + " Y:" + startY);
    }

    public void dragMoveSelectedShape(MouseEvent mouseEvent) {
        logger.log(Level.FINEST, "Moving to X:" + mouseEvent.getX() + " Y:" + mouseEvent.getY());
        clearBox();
        composite.changeXY(mouseEvent.getX() - startX, mouseEvent.getY() - startY);
        momentoDraw(canvas.getGraphicsContext2D());
    }

    @FXML
    protected void deleteSelectedShape() {
        logger.log(Level.INFO, "Deleting selected shapes");
        ArrayList<Decorate> arrayList = composite.getArray();
        for (Decorate decorate : arrayList) {
            momento.remove(decorate);
        }
        clearBox();
        momentoDraw(canvas.getGraphicsContext2D());
    }
}