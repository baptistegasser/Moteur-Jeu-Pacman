package fr.univ.engine.render;

import fr.univ.engine.core.Entity;
import fr.univ.engine.core.math.Point;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Camera {
    private final GraphicsContext ctx;
    private Point pos;
    private double viewportWidth;
    private double viewportHeight;


    public Camera(Canvas canvas) {
        this.ctx = canvas.getGraphicsContext2D();
        this.pos = new Point(0, 0);
        this.viewportWidth = canvas.getWidth();
        this.viewportHeight = canvas.getHeight();
        canvas.widthProperty().addListener(observable -> this.viewportWidth = canvas.getWidth());
        canvas.heightProperty().addListener(observable -> this.viewportHeight = canvas.getHeight());
    }

    public void renderEntity(Entity e, Image texture) {
        double x = e.pos().x - e.size().width/2;
        double y = e.pos().y - e.size().height/2;
        x = x - pos.x + viewportWidth/2;
        y = y - pos.y + viewportHeight/2;

        ctx.drawImage(texture, x, y, e.size().width, e.size().height);
    }

    public void clear() {
        ctx.setFill(Color.BLACK);
        ctx.fillRect(0, 0, viewportWidth, viewportHeight);
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }
}
