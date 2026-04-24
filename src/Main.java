//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.awt.event.KeyEvent;

void main() {
    int canvasWidth = 600;
    int canvasHeight = 600;

    StdDraw.setCanvasSize(canvasWidth, canvasHeight);
    StdDraw.setXscale(0, canvasWidth);
    StdDraw.setYscale(0, canvasHeight);

    StdDraw.clear(StdDraw.WHITE);
    StdDraw.enableDoubleBuffering();

    Chararacter oguz = new Chararacter();
    Zombie zombie = new Zombie();

    long gameStartTime = System.currentTimeMillis();

    StdDraw.picture(oguz.getX(), oguz.getY(), "./wizard.png", 50, 50);
    StdDraw.picture(zombie.getX(), zombie.getY(), zombie.getImagePath(), 50, 50);

    int keyboardPauseDuration = 100;

    while (true) {
        if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
            oguz.moveToLeft();
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
            oguz.moveToRight();

        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
            oguz.moveToUp();
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
            oguz.moveToDown();
        }

        if (isColliding(oguz, zombie)) {
            oguz.loseHealth();
        }
        zombie.move();
        StdDraw.clear(StdDraw.WHITE);

        drawMenu(gameStartTime);

        StdDraw.picture(oguz.getX(), oguz.getY(), "./wizard.png", 50, 50);
        StdDraw.picture(zombie.getX(), zombie.getY(), zombie.getImagePath(), 50, 50);
        oguz.drawHealthBar();

        //oguz hitbox çizdiriyor
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(oguz.getTopLeftCornerX(), oguz.getTopLeftCornerY(), 3);
        StdDraw.filledCircle(oguz.getBottomRightCornerX(), oguz.getBottomRightCornerY(), 3);

        //zombie hitbox çizdiriyor
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledCircle(zombie.getTopLeftCornerX(), zombie.getTopLeftCornerY(), 3);
        StdDraw.filledCircle(zombie.getBottomRightCornerX(), zombie.getBottomRightCornerY(), 3);

        StdDraw.show();
        StdDraw.pause(30);
    }
}

boolean isColliding(Chararacter oguz, Zombie zombie) {
    if (oguz.getTopLeftCornerX() > zombie.getBottomRightCornerX() || zombie.getTopLeftCornerX() > oguz.getBottomRightCornerX()){
        return false;
    }

    if (oguz.getBottomRightCornerY() > zombie.getTopLeftCornerY() || zombie.getBottomRightCornerY() > oguz.getTopLeftCornerY() ){
        return false;
    }
    return true;
}

void drawMenu(long gameStartTime) {
    // Geçen zamanı hesapla (saniye cinsinden)
    long elapsedTime = (System.currentTimeMillis() - gameStartTime) / 1000;

    // Menü arka planı (ekranın tepesine kadar)
    StdDraw.setPenColor(new java.awt.Color(50, 50, 50)); // Koyu gri
    StdDraw.filledRectangle(300, 565, 300, 35); // Daha büyük ve aşağı

    // Zamanı yazı olarak yazdır
    StdDraw.setPenColor(StdDraw.WHITE);
    StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
    StdDraw.text(300, 565, "Geçen Süre: " + elapsedTime + "s");
}
