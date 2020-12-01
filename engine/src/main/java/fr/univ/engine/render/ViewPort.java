package fr.univ.engine.render;

import fr.univ.engine.math.Point;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;

/**
 * A viewport that contains a JavaFX {@link Canvas}.
 */
public class ViewPort {
    /**
     * The canvas that will be used to draw.
     */
    private final Canvas canvas;
    /**
     * Center point of the viewport.
     */
    private final Point center;
    /**
     * Width of the viewport, bindable to another property.
     */
    private final DoubleProperty widthProperty;
    /**
     * Height of the viewport, bindable to another property.
     */
    private final DoubleProperty heightProperty;

    public ViewPort(Canvas canvas) {
        this.canvas = canvas;
        this.center = new Point(0, 0);
        this.widthProperty = new SimpleDoubleProperty(0);
        this.heightProperty = new SimpleDoubleProperty(0);
        this.widthProperty.addListener($ -> this.updateCenter());
        this.heightProperty.addListener($ -> this.updateCenter());
        bindWidthProperty(canvas.widthProperty());
        bindHeightProperty(canvas.heightProperty());
    }

    /**
     * Recalculate the center of this viewport.
     * Called when the width or height property is changed.
     */
    private void updateCenter() {
        setCenter(new Point(getWidth()/2, getHeight()/2));
    }

    /**
     * @return the area where the render is done.
     */
    Canvas getCanvas() {
        return this.canvas;
    }

    /**
     * @return return the width of this viewport.
     */
    double getWidth() {
        return widthProperty.get();
    }

    /**
     * Bind the width property to another property for auto updates.
     *
     * @param observable the observable to bind to
     */
    private void bindWidthProperty(ObservableValue<? extends Number> observable) {
        this.widthProperty.bind(observable);
    }

    /**
     * @return return the height of this viewport.
     */
    double getHeight() {
        return heightProperty.get();
    }

    /**
     * Bind the height property to another property for auto updates.
     *
     * @param observable the observable to bind to
     */
    protected final void bindHeightProperty(ObservableValue<? extends Number> observable) {
        this.heightProperty.bind(observable);
    }

    /**
     * Update the center position.
     *
     * @param pos the new position
     */
    private void setCenter(Point pos) {
        this.center.x = pos.x;
        this.center.y = pos.y;
    }

    /***
     * Convert an object's position relative to the center to
     *  an absolute value that will render correctly on this viewport.
     *
     * @param pos the object's relative position
     * @param w the object's width
     * @param h the object's height
     * @return the absolute position corresponding
     */
    Point toAbsolutePos(Point pos, double w, double h) {
        Point abs = pos.copy();
        abs.x = abs.x - w/2 + center.x;
        abs.y = abs.y - h/2 + center.y;
        return abs;
    }
}
