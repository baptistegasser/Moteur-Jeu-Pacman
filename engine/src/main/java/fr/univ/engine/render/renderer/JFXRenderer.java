package fr.univ.engine.render.renderer;

import fr.univ.engine.math.Point;
import fr.univ.engine.render.RenderEntity;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * A renderer capable of rendering elements on a JavaFX {@link Canvas}.
 */
public class JFXRenderer extends Renderer<Canvas> {

    public JFXRenderer(ViewPort<Canvas> viewport) {
        super(viewport);
    }

    @Override
    public void render(List<RenderEntity> entities) {
        final List<Action> actions = new ArrayList<>();

        for (RenderEntity entity : entities) {
            double w = entity.getRenderObject().width;
            double h = entity.getRenderObject().height;
            Point pos = viewport.toAbsolutePos(entity.getPos(), w, h);
            actions.add(ctx -> ctx.drawImage(entity.getTexture().getImage(), pos.x, pos.y, w, h));
        }

        final GraphicsContext ctx = viewport.getArea().getGraphicsContext2D();
        Platform.runLater(() -> actions.forEach(action -> action.apply(ctx)));
    }

    @FunctionalInterface
    private interface Action {
        void apply(GraphicsContext ctx);
    }
}
