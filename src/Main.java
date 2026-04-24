//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.awt.event.KeyEvent;
import java.util.ArrayList;

void main() {
    int canvasWidth = 600;
    int canvasHeight = 600;

    StdDraw.setCanvasSize(canvasWidth, canvasHeight);
    StdDraw.setXscale(0, canvasWidth);
    StdDraw.setYscale(0, canvasHeight);

    StdDraw.clear(StdDraw.WHITE);
    StdDraw.enableDoubleBuffering();

    Chararacter oguz = new Chararacter();
    ArrayList<Zombie> zombies = new ArrayList<>();
    zombies.add(new Zombie()); // İlk zombi
    ArrayList<Bullet> bullets = new ArrayList<>();

    long gameStartTime = System.currentTimeMillis();
    long lastSpawnTime = gameStartTime;
    int score = 0; // Score değişkeni
    double spawnRate = 0.5; // Saniyede 0.5 zombi = her 2 saniyede 1 zombi

    StdDraw.picture(oguz.getX(), oguz.getY(), "./wizard.png", 50, 50);

    int keyboardPauseDuration = 100;
    boolean spacePressed = false; // Space basılı tutmayı önlemek için


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
        // Space tuşu ile ateş etme
        if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
            if (!spacePressed) {
                bullets.add(new Bullet(oguz.getX(), oguz.getY()));
                spacePressed = true;
            }
        } else {
            spacePressed = false;
        }

        // Spawn rate hesapla (her 10 saniyede bir 0.5 artar)
        long currentTime = System.currentTimeMillis();
        long elapsedSeconds = (currentTime - gameStartTime) / 1000;
        spawnRate = 0.5 + (elapsedSeconds / 10) * 0.5; // Her 10 saniyede +0.5

        // Zombi spawn kontrolü
        long timeSinceLastSpawn = currentTime - lastSpawnTime;
        long spawnInterval = (long)(1000 / spawnRate); // Saniye cinsinden interval milisaniye'ye çevir

        if (timeSinceLastSpawn >= spawnInterval) {
            zombies.add(new Zombie());
            lastSpawnTime = currentTime;
        }

        // Karakterin zombilere çarpması
        for (Zombie z : zombies) {
            if (isColliding(oguz, z)) {
                oguz.loseHealth();
            }
        }

        // Mermi kontrolü
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            b.move();

            boolean bulletHit = false;
            for (int j = 0; j < zombies.size(); j++) {
                Zombie z = zombies.get(j);
                if (isCollidingBulletZombie(b, z)) {
                    bulletHit = true;
                    score += 50;
                    zombies.remove(j);
                    j--;
                }
            }

            if (bulletHit) {
                bullets.remove(i);
                i--;
            } else if (b.isOffScreen()) {
                bullets.remove(i);
                i--;
            }
        }

        // Zombi hareketi ve ekranı aşma kontrolü
        for (int i = 0; i < zombies.size(); i++) {
            Zombie z = zombies.get(i);
            z.move();

            if (z.getY() < 0) {
                score -= 200;
                if (score < 0) score = 0;
                zombies.remove(i);
                i--;
            }
        }

        StdDraw.clear(StdDraw.WHITE);

        drawMenu(gameStartTime, score, spawnRate);

        StdDraw.picture(oguz.getX(), oguz.getY(), "./wizard.png", 50, 50);

        // Tüm zombileri çiz
        for (Zombie z : zombies) {
            StdDraw.picture(z.getX(), z.getY(), z.getImagePath(), 50, 50);
        }

        oguz.drawHealthBar();

        // Mermi çiz
        StdDraw.setPenColor(StdDraw.BLUE);
        for (Bullet b : bullets) {
            StdDraw.filledCircle(b.getX(), b.getY(), 5);
        }

        //oguz hitbox çizdiriyor
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(oguz.getTopLeftCornerX(), oguz.getTopLeftCornerY(), 3);
        StdDraw.filledCircle(oguz.getBottomRightCornerX(), oguz.getBottomRightCornerY(), 3);

        //zombie hitbox çizdiriyor
        StdDraw.setPenColor(StdDraw.BLUE);
        for (Zombie z : zombies) {
            StdDraw.filledCircle(z.getTopLeftCornerX(), z.getTopLeftCornerY(), 3);
            StdDraw.filledCircle(z.getBottomRightCornerX(), z.getBottomRightCornerY(), 3);
        }

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

boolean isCollidingBulletZombie(Bullet bullet, Zombie zombie) {
    if (bullet.getTopLeftCornerX() > zombie.getBottomRightCornerX() || zombie.getTopLeftCornerX() > bullet.getBottomRightCornerX()){
        return false;
    }

    if (bullet.getBottomRightCornerY() > zombie.getTopLeftCornerY() || zombie.getBottomRightCornerY() > bullet.getTopLeftCornerY() ){
        return false;
    }
    return true;
}

void drawMenu(long gameStartTime, int score, double spawnRate) {
    // Geçen zamanı hesapla (saniye cinsinden)
    long elapsedTime = (System.currentTimeMillis() - gameStartTime) / 1000;

    // Menü arka planı (ekranın tepesine kadar)
    StdDraw.setPenColor(new java.awt.Color(50, 50, 50)); // Koyu gri
    StdDraw.filledRectangle(300, 565, 300, 35); // Daha büyük ve aşağı

    // Zamanı yazı olarak yazdır
    StdDraw.setPenColor(StdDraw.WHITE);
    StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
    StdDraw.text(300, 565, "Geçen Süre: " + elapsedTime + "s");
    StdDraw.text(450, 565, "Score: " + score);
    StdDraw.text(500, 565, "Spawn: " + String.format("%.1f", spawnRate));
}
