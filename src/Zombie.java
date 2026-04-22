import java.util.Random;

public class Zombie {
    private double speed;
    private int x;
    private int y;
    private String imagePath;
    private String direction;
    private int hitboxWidth;
    private int hitboxHeight;

    Random rand = new Random();

    public Zombie(){
        speed = 1.0;
        x = rand.nextInt(600);
        y = 600;
        imagePath = "./zombie.png";
        direction = "right";
        hitboxWidth = 50;
        hitboxHeight = 50;
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
