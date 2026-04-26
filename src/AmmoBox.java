import java.util.Random;

public class AmmoBox {
    private int x;
    private int y;
    private double speed;
    private int hitboxWidth;
    private int hitboxHeight;
    private Random rand = new Random();
    private String imagePath;

    /**
     * Constructor for AmmoBox.
     * @param startX
     * @param startY
     */
    public AmmoBox(int startX, int startY) {
        x = startX;
        y = startY;
        speed = 1.0;
        hitboxWidth = 30;
        hitboxHeight = 30;
        imagePath = "mana.png";
    }

    /**
     * Get the x-coordinate of the AmmoBox.
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y-coordinate of the AmmoBox.
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Determines whether the AmmoBox is off the screen.
     *
     * @return true if the AmmoBox is off the screen, false otherwise.
     */
    public boolean isOffScreen() {
        return y < 0;
    }

    /**
     * Calculates the x-coordinate of the top-left corner of the AmmoBox's hitbox.
     *
     * @return The x-coordinate of the top-left corner of the hitbox.
     */
    public double getTopLeftCornerX() {
        return x - hitboxWidth / 2.0;
    }

    /**
     * Calculates the y-coordinate of the top-left corner of the AmmoBox's hitbox.
     * @return The y-coordinate of the top-left corner of the hitbox.
     */
    public double getTopLeftCornerY() {
        return y + hitboxHeight / 2.0;
    }

    /**
     * Calculates the x-coordinate of the bottom-right corner of the AmmoBox's hitbox.
     * @return The x-coordinate of the bottom-right corner of the hitbox.
     */
    public double getBottomRightCornerX() {
        return x + hitboxWidth / 2.0;
    }

    /**
     * Calculates the y-coordinate of the bottom-right corner of the AmmoBox's hitbox.
     * @return The y-coordinate of the bottom-right corner of the hitbox.
     */
    public double getBottomRightCornerY() {
        return y - hitboxHeight / 2.0;
    }

    /**
     * Generates and returns a random amount of ammo within a predefined range.
     *
     * @return A random integer between 1 and 5, representing the amount of ammo.
     */
    public int getAmmoAmount() {
        return rand.nextInt(5) + 1;
    }

    /**
     * Draws the AmmoBox on the canvas.
     */
    public void draw(){
        StdDraw.picture(this.getX(), this.getY(), "./mana.png", 30, 30);
    }
}