package fr.univ.engine.render;

import fr.univ.engine.math.Point;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * A viewport instance represent a zone containing a canvas where drawing can be done.
 */
class ViewPort {
    /**
     * The center of the viewport.
     * Storing allow to reduce calls to width/2 and height/2
     *
     * @see #updateCenter()
     */
    private Point center;
    /**
     * The width of the underlying canvas.
     */
    private double width;
    /**
     * The height of the underlying canvas.
     */
    private double height;
    /**
     * The graphic context of the underlying canvas.
     * The context allow to access drawing function.
     */
    private final GraphicsContext ctx;

    public ViewPort(Canvas canvas) {
        this.width = canvas.getWidth();
        this.height = canvas.getHeight();
        updateCenter();

        this.ctx = canvas.getGraphicsContext2D();

        // When th canvas width or height change, set the new value
        canvas.widthProperty().addListener(o -> setWidth(canvas.getWidth()));
        canvas.heightProperty().addListener(o -> setHeight(canvas.getHeight()));
    }

    /**
     * Draw an image on the canvas.
     *
     * @param img the image to draw.
     * @param x the X position of the image
     * @param y the Y position of the image
     * @param w the target width to render (might be different from the image width)
     * @param h the target height to render (might be different from the image height)
     */
    public void drawImage(Image img, double x, double y, double w, double h) {
        double x1 = x - w / 2 + center.x;
        double y1 = y - h / 2 + center.y;

        // Skip drawing image out of viewport
        if (x1 >= this.width || y1 >= this.height || (x1 + w) <= 0 || (y1 + w) <= 0) {
            return;
        }

        RenderEngine.runOnFXThread(() -> ctx.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), x1, y1, w, h));
    }

    /**
     * Clear the display.
     */
    public void clear() {
        RenderEngine.runOnFXThread(() -> {
            ctx.setFill(Color.BLACK);
            ctx.fillRect(0, 0, width, height);
        });
    }

    /**
     * Set the width and update the center.
     *
     * @param width the new viewport width
     */
    private void setWidth(double width) {
        this.width = width;
        updateCenter();
        clear(); // clear display to clean last render made on different size
    }

    /**
     * Set the height and update the center.
     *
     * @param height the new viewport height
     */
    private void setHeight(double height) {
        this.height = height;
        updateCenter();
        clear(); // clear display to clean last render made on different size
    }

    /**
     * Calculate the center based on the width and height.
     * Storing this value reduce number of time the math is down.
     */
    private void updateCenter() {
        this.center = new Point(width / 2, height / 2);
    }

    public void drawRect(double x, double y, double w, double h) {
        double x1 = x - w / 2 + center.x;
        double y1 = y - h / 2 + center.y;

        RenderEngine.runOnFXThread(() -> {
            ctx.setStroke(Color.RED);
            ctx.beginPath();
            ctx.rect(x1, y1, w, h);
            ctx.stroke();
        });
    }

    public void drawCircle(double centerX, double centerY, double radius) {
        double x1 = (centerX + center.x) - radius;
        double y1 = (centerY + center.y) - radius;

        RenderEngine.runOnFXThread(() -> {
            ctx.setStroke(Color.GREEN);
            ctx.strokeOval(x1, y1, radius*2, radius*2);
        });
    }
}
