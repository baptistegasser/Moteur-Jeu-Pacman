package fr.univ.engine.render;

import fr.univ.engine.core.Core;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;

/**
 * The JavaFX application implementation used to create a window and display content.
 */
public final class JFXApp extends Application {
    /**
     * The window's configuration.
     */
    private static WindowConfig window;
    /**
     * A latch used to detect when the JavaFX app thread is started.
     */
    private static final CountDownLatch latch = new CountDownLatch(1);

    /**
     * The Window of our app.
     */
    private static Stage stage;
    /**
     * Canvas where entities can be rendered.
     */
    public static Canvas canvas;
    /**
     * StackPane allowing to stack elements on top of the Canvas.
     */
    public static StackPane stackPane;

    /**
     * Start the app and wait for it to be started.
     *
     * @throws InterruptedException if the thread was interrupted while waiting
     */
    public static void startApp() throws InterruptedException {
        new Thread(Application::launch).start();
        latch.await();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        canvas = new Canvas(window.width, window.height);
        stackPane = new StackPane(canvas);

        stage = primaryStage;
        stage.setWidth(window.width);
        stage.setHeight(window.height);
        stage.setTitle(window.title);
        stage.setScene(new Scene(stackPane));

        latch.countDown();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Core.quit();
    }

    /**
     * Show the window if it's not showing.
     */
    public static void showWindow() {
        if (stage != null && !stage.isShowing()) {
            Platform.runLater(stage::show);
        }
    }

    /**
     * Hide the window if it's showing.
     */
    public static void hideWindow() {
        if (stage != null && stage.isShowing()) {
            Platform.runLater(stage::hide);
        }
    }

    /**
     * Set the configuration of the window.
     * Will not affect the window after the app start.
     *
     * @param window the config
     */
    public static void setWindowConfig(WindowConfig window) {
        JFXApp.window = window;
    }
}
