import java.util.Random;

public class AmmoBox {
    private int x;
    private int y;
    private double speed;
    private int hitboxWidth;
    private int hitboxHeight;
    private Random rand = new Random();

    public AmmoBox(int startX, int startY) {
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

    public int getAmmoAmount() {
        return rand.nextInt(5) + 1; // 1-5 arası rastgele
    }
}