import java.util.Random;

public class HealthBox {
    private int x;
    private int y;
    private double speed;
    private int hitboxWidth;
    private int hitboxHeight;

    public HealthBox(int startX, int startY) {
        x = startX;
        y = startY;
        speed = 1.0;
        hitboxWidth = 30;
        hitboxHeight = 30;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move() {
        y -= speed;
    }

    public boolean isOffScreen() {
        return y < 0;
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

    public int getHealthAmount() {
        return 1; // Her kutudan 1 can
    }
}