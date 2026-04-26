/**
 * The type Chararacter.
 */
public class Chararacter {
    private double  speed;
    private String imagePath;
    private int x;
    private int y;
    private int health;
    private int hitboxWidth;
    private int hitboxHeight;
    private long lastDamageTime = 0;
    private static final long INVINCIBILITY_DURATION = 3000;
    private static final int MIN_X = 25;
    private static final int MAX_X = 575;
    private static final int MIN_Y = 50;
    private static final int MAX_Y = 500;
    private static final int MAX_HEALTH = 4;
    private int ammo;

    /**
     * Constructs a new character with default attributes.
     * The default attributes are:
     * - Speed: 5.0
     * - Image path: "./wizard.png"
     * - Initial position: (300, 100)
     * - Health: 3
     * - Hitbox dimensions: 50 (width) x 50 (height)
     * - Ammo count: 20
     */
    Chararacter(){
        speed = 5.0;
        imagePath = "./wizard.png";
        x = 300;
        y = 100;
        health = 3;
        hitboxWidth = 50;
        hitboxHeight = 50;
        ammo = 20;
    }

    /**
     * Get the x-coordinate of the character.
     *
     * @return The x-coordinate.
     */
    public int getX(){
        return x;
    }

    /**
     * Get the y-coordinate of the character.
     *
     * @return The y-coordinate.
     */
    public int getY(){
        return y;
    }

    /**
     * Retrieves the current health value of the character.
     *
     * @return The current health value.
     */
    public int getHealth(){
        return health;
    }

    /**
     * Retrieves the current amount of ammo the character has.
     *
     * @return The current ammo count.
     */
    public int getAmmo(){
        return ammo;
    }

    /**
     * Adds ammo.
     *
     * @param amount the amount
     */
    public void addAmmo(int amount) {
        ammo += amount;
    }

    /**
     * Adds health.
     *
     * @param amount the amount
     */
    public void addHealth(int amount) {
        health += amount;
        if (health > MAX_HEALTH) {
            health = MAX_HEALTH;
        }
    }

    /**
     * Checks if the character can shoot.
     *
     * @return true if ammo is available, false otherwise
     */
    public boolean canShoot() {
        return ammo > 0;
    }

    /**
     * Shoots if ammo grater than 0.
     */
    public void shoot() {
        if (ammo > 0) {
            ammo--;
        }
    }

    /**
     * Moves the character to the left by decreasing its x-coordinate.
     * Characters cannot move beyond the specified boundaries.
     */
    public void moveToLeft(){
        x -= speed;
        if (x < MIN_X) x = MIN_X;
    }

    /**
     * Moves the character to the right by increasing its x-coordinate.
     * Characters cannot move beyond the specified boundaries.
     */
    public void moveToRight(){
        x += speed;
        if (x > MAX_X) x = MAX_X;
    }

    /**
     * Moves the character up by increasing its y-coordinate.
     * Characters cannot move beyond the specified boundaries.
     */
    public void moveToUp(){
        y += speed;
        if (y > MAX_Y) y = MAX_Y;
    }

    /**
     * Moves the character down by decreasing its y-coordinate.
     * Characters cannot move beyond the specified boundaries.
     */
    public void moveToDown(){
        y -= speed;
        if (y < MIN_Y) y = MIN_Y;
    }

    /**
     * Gets the x-coordinate of the top-left corner of the character's hitbox.
     *
     * @return the x-coordinate of the hitbox's top-left corner
     */
    public double getTopLeftCornerX() {
        return x - hitboxWidth / 2.0;
    }

    /**
     * Gets the y-coordinate of the top-left corner of the character's hitbox.
     *
     * @return the y-coordinate of the hitbox's top-left corner
     */
    public double getTopLeftCornerY() {
        return y + hitboxHeight / 2.0;
    }

    /**
     * Gets the x-coordinate of the bottom-right corner of the character's hitbox.
     *
     * @return the x-coordinate of the hitbox's bottom-right corner
     */
    public double getBottomRightCornerX() {
        return x + hitboxWidth / 2.0;
    }

    /**
     * Gets the y-coordinate of the bottom-right corner of the character's hitbox.
     *
     * @return the y-coordinate of the hitbox's bottom-right corner
     */
    public double getBottomRightCornerY() {
        return y - hitboxHeight / 2.0;
    }

    /**
     * Decreases the health of the character by 1.
     */
    public void loseHealth() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastDamageTime < INVINCIBILITY_DURATION) {
            return;
        }

        if (health > 0) {
            health--;
            lastDamageTime = currentTime;
        }
    }

    /**
     * Draws the health bar at the bottom of the character.
     */
    public void drawHealthBar() {
        double heartSize = 15;
        double spacing = 20;
        double startX = x - ((health - 1) * spacing / 2.0) + 2;
        double startY = y - 40;

        for (int i = 0; i < health; i++) {
            double heartX = startX + (i * spacing);
            StdDraw.picture(heartX, startY, "./heart.png", heartSize, heartSize);
        }
    }

    /**
     * Draws the character on the canvas.
     */
    public void draw(){
        StdDraw.picture(this.getX(), this.getY(), "./wizard.png", 50, 50);
    }
}
