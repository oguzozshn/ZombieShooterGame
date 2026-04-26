import java.util.ArrayList;
import java.util.Random;

public class HealthBox {
    private int x;
    private int y;
    private double speed;
    private int hitboxWidth;
    private int hitboxHeight;

    /**
     * Constructor for the HealthBox class.
     * @param startX
     * @param startY
     */
    public HealthBox(int startX, int startY) {
        x = startX;
        y = startY;
        speed = 1.0;
        hitboxWidth = 30;
        hitboxHeight = 30;
    }

    /**
     * Get the x-coordinate of the health box.
     * @return x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y-coordinate of the health box.
     * @return y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Get the top-left corner x-coordinate of the health box's hitbox.
     * @return top-left corner x-coordinate
     */
    public double getTopLeftCornerX() {
        return x - hitboxWidth / 2.0;
    }

    /**
     * Get the top-left corner y-coordinate of the health box's hitbox.
     * @return top-left corner y-coordinate
     */
    public double getTopLeftCornerY() {
        return y + hitboxHeight / 2.0;
    }

    /**
     * Get the bottom-right corner x-coordinate of the health box's hitbox.
     * @return bottom-right corner x-coordinate
     */
    public double getBottomRightCornerX() {
        return x + hitboxWidth / 2.0;
    }

    /**
     * Get the bottom-right corner y-coordinate of the health box's hitbox.
     * @return bottom-right corner y-coordinate
     */
    public double getBottomRightCornerY() {
        return y - hitboxHeight / 2.0;
    }

    /**
     * Get the health amount of the health box.
     * @return health amount
     */
    public int getHealthAmount() {
        return 1;
    }

    /**
     * Draws the health box on the canvas.
     */
    public void draw(){
        StdDraw.picture(this.getX(), this.getY(), "./heart.png", 20, 20);
    }
}