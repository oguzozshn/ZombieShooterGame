public class Chararacter {
    private double  speed;
    private String imagePath;
    private int x;
    private int y;
    private int health;
    private int hitboxWidth;
    private int hitboxHeight;


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
    }

    public void moveToRight(){
        x += speed;
    }

    public void moveToUp(){
        y += speed;
    }

    public void moveToDown(){
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
