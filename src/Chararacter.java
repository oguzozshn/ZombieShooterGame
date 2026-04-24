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
    private static final int MIN_Y = 25; // hitboxHeight/2
    private static final int MAX_Y = 545; // 600 - 55 (menü) - hitboxHeight/2


    Chararacter(){
        speed = 5.0;
        imagePath = "./wizard.png";
        x = 500;
        y = 500;
        health = 3;
        hitboxWidth = 50;
        hitboxHeight = 50;
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
            lastDamageTime = currentTime; // Son hasar zamanını güncelle
        }
    }

    public void drawHealthBar() {
        double startX = x - 30;  // Karakterin altında başla
        double startY = y - 40;  // Karakterin 40 pixel altında

        // Her can için bir kalp çiz
        for (int i = 0; i < health; i++) {
            double heartX = startX + (i * 30);  // Her kalp 20 pixel aralıkla
            StdDraw.picture(heartX, startY, "./heart.png", 15, 15);
        }
    }


}
