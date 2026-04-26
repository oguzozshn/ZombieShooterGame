public class Bullet {
    private int x;
    private int y;
    private double speed;
    private int hitboxWidth;
    private int hitboxHeight;

    public Bullet(int startX, int startY) {
        x = startX;
        y = startY;
        speed = 12.0;
        hitboxWidth = 10;
        hitboxHeight = 10;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move() {
        y += speed;
    }

    public boolean isOffScreen() {
        return y > 600;
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
    
    public void draw(){
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledCircle(this.getX(), this.getY(), 5);
    }
}