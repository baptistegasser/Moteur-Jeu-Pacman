package fr.univ.engine.render.renderer;

import fr.univ.engine.math.Point;
import fr.univ.engine.render.entity.RenderEntity;
import fr.univ.engine.render.texture.Texture;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * A renderer capable of rendering elements on a JavaFX {@link Canvas}.
 */
public class JFXRenderer extends Renderer<Canvas> {

    public JFXRenderer(ViewPort<Canvas> viewport) {
        super(viewport);
    }

    @Override
    public void render(List<RenderEntity> entities) {
        final List<GraphicAction> actions = new ArrayList<>();

        for (RenderEntity entity : entities) {
            Texture texture = entity.getTexture();
            if (texture == null) continue;
            Point pos = viewport.toAbsolutePos(entity.pos(), texture.width(), texture.height());
            actions.add(ctx -> ctx.drawImage(texture.getImage(), pos.x, pos.y, texture.width(), texture.height()));
        }

        final GraphicsContext ctx = viewport.getArea().getGraphicsContext2D();
        Platform.runLater(() -> {
            ctx.setFill(Color.BLACK);
            ctx.fillRect(0, 0, viewport.getWidth(), viewport.getHeight());
            actions.forEach(action -> action.apply(ctx));
        });
    }

    /**
     * Simple functional interface to store graphical change for later.
     * Allow to batch multiple drawing actions.
     */
    @FunctionalInterface
    private interface GraphicAction {
        void apply(GraphicsContext ctx);
    }
}
