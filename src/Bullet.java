public class Bullet {
    private int x;
    private int y;
    private double speed;
    private int hitboxWidth;
    private int hitboxHeight;

    /**
     * Constructor for the Bullet class.
     * @param startX
     * @param startY
     */
    public Bullet(int startX, int startY) {
        x = startX;
        y = startY;
        speed = 12.0;
        hitboxWidth = 10;
        hitboxHeight = 10;
    }

    /**
     * Get the x-coordinate of the bullet.
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y-coordinate of the bullet.
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Move the bullet upwards.
     */
    public void move() {
        y += speed;
    }

    /**
     * Check if the bullet is off the screen.
     * @return
     */
    public boolean isOffScreen() {
        return y > 600;
    }

    /**
     * Get the x-coordinate of the top-left corner of the bullet's hitbox.
     * @return
     */
    public double getTopLeftCornerX() {
        return x - hitboxWidth / 2.0;
    }

    /**
     * Returns the y-coordinate of the top-left corner of the bullet's hitbox.
     * @return the y-coordinate of the top-left corner of the hitbox.
     */
    public double getTopLeftCornerY() {
        return y + hitboxHeight / 2.0;
    }

    /**
     * Get the x-coordinate of the bottom-right corner of the bullet's hitbox.
     * @return the x-coordinate of the bottom-right corner of the hitbox.
     */
    public double getBottomRightCornerX() {
        return x + hitboxWidth / 2.0;
    }

    /**
     * Get the y-coordinate of the bottom-right corner of the bullet's hitbox.
     * @return the y-coordinate of the bottom-right corner of the hitbox.
     */
    public double getBottomRightCornerY() {
        return y - hitboxHeight / 2.0;
    }

    /**
     * Draw the bullet on canvas.
     */
    public void draw(){
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledCircle(this.getX(), this.getY(), 5);
    }
}