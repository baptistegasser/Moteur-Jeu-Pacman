package fr.univ.engine.render.renderer;

import fr.univ.engine.math.Point;
import javafx.scene.canvas.Canvas;

/**
 * A viewport that proxy a JavaFX {@link Canvas}.
 */
public class CanvasViewPort extends ViewPort<Canvas> {

    public CanvasViewPort(Canvas view) {
        super(view);
        widthProperty.bind(view.widthProperty());
        heightProperty.bind(view.heightProperty());
    }

    @Override
    protected void updateCenter() {
        setCenter(new Point(getWidth()/2, getHeight()/2));
    }
}
