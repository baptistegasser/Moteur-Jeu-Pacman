package fr.univ.engine.render;

import com.sun.javafx.application.PlatformImpl;
import fr.univ.engine.core.Config;
import fr.univ.engine.io.KeyEventHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
     * The config of the game.
     * Used to setup the window.
     */
    private static Config config;
    /**
     * A latch used to detect when the JavaFX app thread is started.
     */
    private static final CountDownLatch latch = new CountDownLatch(1);

    /**
     * Property following the status of the app.
     * If the app is closing (closed the window) {@link ReadOnlyBooleanWrapper#getValue()} will return true.
     * The property is read only to allow listeners to read but only this app to modify it.
     */
    private static final ReadOnlyBooleanWrapper isClosing = new ReadOnlyBooleanWrapper();

    /**
     * The Window of our app.
     */
    private static Stage stage;
    /**
     * Renderer used by this app.
     */
    private static Renderer renderer;
    /**
     * StackPane allowing to stack elements on top of the Canvas.
     */
    public static StackPane uiRoot;

    /**
     * Start the app and wait for it to be started.
     *
     * @throws InterruptedException if the thread was interrupted while waiting
     */
    public static void startAndWaitUntilReady() throws InterruptedException {
        new Thread(Application::launch).start();
        latch.await();
    }

    /**
     * Set a {@link KeyEventHandler} as the handler for key events.
     *
     * @param handler the new handler
     */
    public static void setKeyEventHandler(KeyEventHandler handler) {
        stage.getScene().setOnKeyPressed(handler::notifyKeyPressed);
        stage.getScene().setOnKeyReleased(handler::notifyKeyReleased);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle(config.title);
        stage.setWidth(config.width);
        stage.setHeight(config.height);
        stage.setResizable(config.resizable);

        Canvas canvas = new Canvas(config.width, config.height);
        uiRoot = new StackPane();
        StackPane root = new StackPane();
        root.getChildren().addAll(canvas, uiRoot);
        canvas.widthProperty().bind(stage.widthProperty());
        canvas.heightProperty().bind(stage.heightProperty());
        renderer = new Renderer(new ViewPort(canvas));

        stage.setScene(new Scene(root));
        stage.setAlwaysOnTop(true);

        primaryStage.setOnCloseRequest(t -> stop());

        latch.countDown();
    }

    @Override
    public void stop() {
        isClosing.set(true);
        PlatformImpl.exit();
    }

    /**
     * Show the window if it's not showing.
     *
     * @throws InterruptedException if the thread was interrupted while
     * waiting for the window to show.
     */
    public static void showWindow() throws InterruptedException {
        CountDownLatch showLatch = new CountDownLatch(1);
        ChangeListener<Boolean> listener = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    showLatch.countDown();
                    stage.showingProperty().removeListener(this);
                }
            }
        };
        stage.showingProperty().addListener(listener);
        if (stage != null && !stage.isShowing()) {
            Platform.runLater(stage::show);
            showLatch.await();
        }
    }

    /**
     * Hide the window if it's showing.
     *
     * @throws InterruptedException if the thread was interrupted while
     * waiting for the window to hide.
     */
    public static void hideWindow() throws InterruptedException {
        CountDownLatch hideLatch = new CountDownLatch(1);
        ChangeListener<Boolean> listener = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    hideLatch.countDown();
                    stage.showingProperty().removeListener(this);
                }
            }
        };
        stage.showingProperty().addListener(listener);
        if (stage != null && stage.isShowing()) {
            Platform.runLater(stage::hide);
            hideLatch.await();
        }
    }

    /**
     * Set the configuration of the game.
     * Will not affect the window after the app start.
     *
     * @param config the game config,
     */
    public static void setConfig(Config config) {
        JFXApp.config = config;
    }

    /**
     * @return the read only property value of {@link #isClosing}
     */
    public static ReadOnlyBooleanProperty getIsClosingProperty() {
        return isClosing.getReadOnlyProperty();
    }

    /**
     * Retrieve the render used by the app.
     *
     * @return the renderer instance
     */
    public static Renderer getRenderer() {
        return JFXApp.renderer;
    }
}