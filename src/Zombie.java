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
    private int randomDirection; // Rastgele yön için

    public Zombie(){
        speed = 1.0;
        x = rand.nextInt(600);
        y = 600;
        imagePath = "./zombie.png";
        direction = "right";
        hitboxWidth = 50;
        hitboxHeight = 50;
        randomDirection = rand.nextInt(3) - 1;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }

    public String getImagePath(){
        return imagePath;
    }

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
        
        if (rand.nextDouble() < 0.02) {
            randomDirection = rand.nextInt(3) - 1;
        }
    }

    public int getHitboxWidth() {
        return hitboxWidth;
    }

    public int getHitboxHeight() {
        return hitboxHeight;
    }

    public double getTopLeftCornerX() {
        return x - hitboxWidth / 2.0;
    }

    public double getTopLeftCornerY() {
        return y + hitboxHeight / 2.0;
    }

    public double getBottomRightCornerX() {
        return x + hitboxWidth / 2.0;
    }

    public double getBottomRightCornerY() {
        return y - hitboxHeight / 2.0;
    }
}
