import java.util.Random;

public class Zombie {
    private double speed;
    private int x;
    private int y;
    private String imagePath;
    private String direction;
    private int hitboxWidth;
    private int hitboxHeight;
    private Random rand = new Random();
    private int randomDirection;

    /**
     * Constructor for the Zombie class.
     */
    public Zombie(){
        speed = 1.0;
        x = rand.nextInt(600);
        y = 500;
        imagePath = "./zombie.png";
        direction = "right";
        hitboxWidth = 50;
        hitboxHeight = 50;
        randomDirection = rand.nextInt(5) - 1;
    }

    /**
     * Get the x-coordinate of the zombie.
     * @return x-coordinate
     */
    public int getX(){
        return x;
    }

    /**
     * Get the y-coordinate of the zombie.
     * @return y-coordinate
     */
    public int getY(){
        return y;
    }

    /**
     * Get the image path of the zombie.
     * @return image path
     */
    public String getImagePath(){
        return imagePath;
    }

    /**
     * Moves the zombie by updating its x and y coordinates based on its speed
     * and random horizontal direction.
     *
     * The vertical movement decreases the y-coordinate by the zombie's speed.
     * The horizontal movement modifies the x-coordinate based on the current
     * random direction. If the zombie reaches the left or right boundary (0 or 600),
     * its position is corrected, and the horizontal direction is changed accordingly.
     *
     * Additionally, the random horizontal direction may change with a small
     * probability of 10%.
     */
    public void move(){
        y -= speed;
        
        x += randomDirection;
        
        if (x < 0) {
            x = 0;
            randomDirection = 1;
        } else if (x > 600) {
            x = 600;
            randomDirection = -1;
        }
        
        if (rand.nextDouble() < 0.1) {
            randomDirection = rand.nextInt(3) - 1;
        }
    }

    /**
     * Calculates the x-coordinate of the top-left corner of the zombie's hitbox.
     * @return The x-coordinate of the top-left corner of the hitbox
     */
    public double getTopLeftCornerX() {
        return x - hitboxWidth / 2.0;
    }

    /**
     * Calculates the y-coordinate of the top-left corner of the zombie's hitbox.
     * @return The y-coordinate of the top-left corner of the hitbox
     */
    public double getTopLeftCornerY() {
        return y + hitboxHeight / 2.0;
    }

    /**
     * Calculates the x-coordinate of the bottom-right corner of the zombie's hitbox.
     * @return The x-coordinate of the bottom-right corner of the hitbox
     */
    public double getBottomRightCornerX() {
        return x + hitboxWidth / 2.0;
    }

    /**
     * Calculates the y-coordinate of the bottom-right corner of the zombie's hitbox.
     * @return The y-coordinate of the bottom-right corner of the hitbox
     */
    public double getBottomRightCornerY() {
        return y - hitboxHeight / 2.0;
    }

    /**
     * Draws the zombie on the screen.
     */
    public void draw(){
        StdDraw.picture(this.getX(), this.getY(), this.getImagePath(), 50, 50);
    }
}
