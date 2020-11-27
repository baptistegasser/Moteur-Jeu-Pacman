package fr.univ.engine.render;

import com.sun.javafx.application.PlatformImpl;
import fr.univ.engine.io.KeyEventHandler;
import fr.univ.engine.render.config.WindowConfig;
import fr.univ.engine.render.renderer.CanvasViewPort;
import fr.univ.engine.render.renderer.JFXRenderer;
import fr.univ.engine.render.renderer.Renderer;
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
     * The window's configuration.
     */
    private static WindowConfig window;
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
    private static Renderer<?> renderer;
    /**
     * StackPane allowing to stack elements on top of the Canvas.
     */
    public static StackPane stackPane;

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
        stage.getScene().setOnKeyPressed(handler::onKeyPressed);
        stage.getScene().setOnKeyReleased(handler::onKeyReleased);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setWidth(window.width);
        stage.setHeight(window.height);
        stage.setTitle(window.title);
        stage.setResizable(window.allowResize);

        Canvas canvas = new Canvas(window.width, window.height);
        stackPane = new StackPane(canvas);
        canvas.widthProperty().bind(stage.widthProperty());
        canvas.heightProperty().bind(stage.heightProperty());
        renderer = new JFXRenderer(new CanvasViewPort(canvas));

        stage.setScene(new Scene(stackPane));

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
     */
    public static void showWindow() {
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
        }
        try {
            showLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
    public static Renderer<?> getRenderer() {
        return JFXApp.renderer;
    }
}