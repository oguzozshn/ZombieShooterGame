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
    private static final int MIN_X = 25; // hitboxWidth/2
    private static final int MAX_X = 575; // 600 - hitboxWidth/2
    private static final int MIN_Y = 25;
    private static final int MAX_Y = 545;
    private static final int MAX_HEALTH = 3;
    
    // Mermi sistemi
    private int ammo;

    Chararacter(){
        speed = 5.0;
        imagePath = "./wizard.png";
        x = 500;
        y = 500;
        health = 3;
        hitboxWidth = 50;
        hitboxHeight = 50;
        ammo = 20;
    }

    public int getX(){
        return x;
    }

    public void setX(int x){
    }

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getHealth(){
        return health;
    }

    public int getAmmo(){
        return ammo;
    }

    public void addAmmo(int amount) {
        ammo += amount;
    }

    public void addHealth(int amount) {
        health += amount;
        if (health > MAX_HEALTH) {
            health = MAX_HEALTH; // Maksimum can üçü geçemesin
        }
    }

    public boolean canShoot() {
        return ammo > 0;
    }

    public void shoot() {
        if (ammo > 0) {
            ammo--;
        }
    }

    public void moveToLeft(){
        x -= speed;
        if (x < MIN_X) x = MIN_X;
    }

    public void moveToRight(){
        x += speed;
        if (x > MAX_X) x = MAX_X;
    }

    public void moveToUp(){
        y += speed;
        if (y > MAX_Y) y = MAX_Y;
    }

    public void moveToDown(){
        y -= speed;
        if (y < MIN_Y) y = MIN_Y;
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

    public void drawHealthBar() {
        double startX = x - 30;
        double startY = y - 40;

        for (int i = 0; i < health; i++) {
            double heartX = startX + (i * 30);
            StdDraw.picture(heartX, startY, "./heart.png", 15, 15);
        }
    }
}
