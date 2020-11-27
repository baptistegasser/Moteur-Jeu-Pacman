package fr.univ.engine.render.renderer;

import fr.univ.engine.math.Point;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;

/**
 * Representation of the area where the render is done.
 * This is a proxy for the underlying {@link #area} object.
 *
 * @param <T> the type of the area used to render, ie: a {@link javafx.scene.canvas.Canvas}.
 */
public abstract class ViewPort<T> {
    /**
     * The area where the render is done.
     */
    private final T area;

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

    public ViewPort(T view) {
        this.area = view;
        this.center = new Point(0, 0);
        this.widthProperty = new SimpleDoubleProperty(0);
        this.heightProperty = new SimpleDoubleProperty(0);
        this.widthProperty.addListener($ -> this.updateCenter());
        this.heightProperty.addListener($ -> this.updateCenter());
    }

    /**
     * Recalculate the center of this viewport.
     * Called when the width or height property is changed.
     */
    protected abstract void updateCenter();

    /**
     * @return the area where the render is done.
     */
    public T getArea() {
        return area;
    }

    /**
     * @return return the width of this viewport.
     */
    public double getWidth() {
        return widthProperty.get();
    }

    /**
     * Bind the width property to another property for auto updates.
     *
     * @param observable the observable to bind to
     */
    protected final void bindWidthProperty(ObservableValue<? extends Number> observable) {
        this.widthProperty.bind(observable);
    }

    /**
     * @return return the height of this viewport.
     */
    public double getHeight() {
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
    protected void setCenter(Point pos) {
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
    public Point toAbsolutePos(Point pos, double w, double h) {
        Point abs = pos.copy();
        abs.x = abs.x - w/2 + center.x;
        abs.y = abs.y - h/2 + center.y;
        return abs;
    }
}
