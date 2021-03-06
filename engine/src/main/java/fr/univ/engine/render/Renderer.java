package fr.univ.engine.render;

import fr.univ.engine.core.TransformComponent;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.math.Point;
import fr.univ.engine.render.texture.Texture;
import fr.univ.engine.render.texture.TextureComparator;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.List;

/**
 * A renderer capable of rendering elements on a JavaFX {@link Canvas}.
 */
public class Renderer {
    /**
     * The viewport containing the canvas.
     */
    protected final ViewPort viewport;

    public Renderer(ViewPort viewport) {
        this.viewport = viewport;
    }

    /**
     * Render a list of entities on the viewport.
     *
     * @param entities the entities to render.
     */
    public void render(List<Entity> entities) {
        final List<GraphicAction> actions = new ArrayList<>();

        // Sort the entities by z-index
        entities.sort((o1, o2) -> {
            Texture t1 = o1.getComponent(RenderComponent.class).getTexture();
            Texture t2 = o2.getComponent(RenderComponent.class).getTexture();
            return new TextureComparator().compare(t1, t2);
        });

        for (Entity entity : entities) {
            Texture texture = entity.getComponent(RenderComponent.class).getTexture();
            if (texture == null) continue;

            TransformComponent transform = entity.getComponent(TransformComponent.class);
            Point pos = viewport.toAbsolutePos(transform.position(), texture.width(), texture.height());

            double x  = pos.xValue();
            double y  = pos.yValue();

            if ((transform.rotation() % 360) == 0) {
                actions.add(ctx -> ctx.drawImage(texture.getImage(), x, y, texture.width(), texture.height()));
            } else {
                actions.add(ctx -> {
                    ctx.save();
                    Rotate rt = new Rotate(transform.rotation(), x + texture.width()/2, y + texture.height()/2);
                    ctx.setTransform(rt.getMxx(), rt.getMyx(), rt.getMxy(), rt.getMyy(), rt.getTx(), rt.getTy());
                    ctx.drawImage(texture.getImage(), x, y, texture.width(), texture.height());
                    ctx.restore();
                });
            }
        }

        final GraphicsContext ctx = viewport.getCanvas().getGraphicsContext2D();
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
