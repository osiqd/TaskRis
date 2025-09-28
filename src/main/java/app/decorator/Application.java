/**
 * Main application class that launches the JavaFX application
 */

package app.decorator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.*;

public class Application extends javafx.application.Application {
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    static {
        try {
            FileHandler fh = new FileHandler("logs/application.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger", e);
        }
    }

    @Override
    public void start(Stage stage) {
        logger.log(Level.INFO, "Application starting");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
            stage.setTitle("Decorator");
            stage.setScene(scene);
            stage.show();
            logger.log(Level.INFO, "Application started successfully");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load FXML file", e);
        }
    }

    public static void main(String[] args) {
        logger.log(Level.INFO, "Launching application");
        launch();
    }
}