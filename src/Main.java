//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

void main() {
    int canvasWidth = 600;
    int canvasHeight = 600;

    StdDraw.setCanvasSize(canvasWidth, canvasHeight);
    StdDraw.setXscale(0, canvasWidth);
    StdDraw.setYscale(0, canvasHeight);

    StdDraw.clear(StdDraw.WHITE);
    StdDraw.enableDoubleBuffering();

    boolean gameRunning = true;
    
    while (gameRunning) {
        gameRunning = runGame(canvasWidth, canvasHeight);
    }
}

boolean runGame(int canvasWidth, int canvasHeight) {
    Chararacter oguz = new Chararacter();
    ArrayList<Zombie> zombies = new ArrayList<>();
    zombies.add(new Zombie());
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<AmmoBox> ammoBoxes = new ArrayList<>();

    long gameStartTime = System.currentTimeMillis();
    long lastSpawnTime = gameStartTime;
    long lastAmmoSpawnTime = gameStartTime;
    int score = 0;
    double spawnRate = 0.5;

    int keyboardPauseDuration = 100;
    boolean spacePressed = false;
    Random rand = new Random();

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

        if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
            if (!spacePressed && oguz.canShoot()) {
                bullets.add(new Bullet(oguz.getX(), oguz.getY()));
                oguz.shoot();
                spacePressed = true;
            }
        } else {
            spacePressed = false;
        }

        long currentTime = System.currentTimeMillis();
        long elapsedSeconds = (currentTime - gameStartTime) / 1000;
        spawnRate = 0.5 + (elapsedSeconds / 10) * 0.5;

        // Zombie spawn
        long timeSinceLastSpawn = currentTime - lastSpawnTime;
        long spawnInterval = (long)(1000 / spawnRate);
        
        if (timeSinceLastSpawn >= spawnInterval) {
            zombies.add(new Zombie());
            lastSpawnTime = currentTime;
        }

        // AmmoBox spawn (rastgele: ortalama her 5 saniyede bir)
        long timeSinceLastAmmoSpawn = currentTime - lastAmmoSpawnTime;
        if (timeSinceLastAmmoSpawn >= 5000) {
            if (rand.nextDouble() < 0.3) { // %30 şans
                int randomX = rand.nextInt(550) + 25; // 25-575 arasında
                ammoBoxes.add(new AmmoBox(randomX, 600));
            }
            lastAmmoSpawnTime = currentTime;
        }

        // Karakterin zombilere çarpması
        for (Zombie z : zombies) {
            if (isColliding(oguz, z)) {
                oguz.loseHealth();
            }
        }

        // Karakterin ammo boxlarına çarpması
        for (int i = 0; i < ammoBoxes.size(); i++) {
            AmmoBox box = ammoBoxes.get(i);
            if (isCollidingCharacterAmmoBox(oguz, box)) {
                oguz.addAmmo(box.getAmmoAmount());
                ammoBoxes.remove(i);
                i--;
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

        // AmmoBox hareketi
        for (int i = 0; i < ammoBoxes.size(); i++) {
            AmmoBox box = ammoBoxes.get(i);
            box.move();
            if (box.isOffScreen()) {
                ammoBoxes.remove(i);
                i--;
            }
        }

        // Zombie hareketi
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

        drawMenu(gameStartTime, score, spawnRate, oguz.getAmmo());

        StdDraw.picture(oguz.getX(), oguz.getY(), "./wizard.png", 50, 50);
        
        for (Zombie z : zombies) {
            StdDraw.picture(z.getX(), z.getY(), z.getImagePath(), 50, 50);
        }
        
        // AmmoBox çiz
        for (AmmoBox box : ammoBoxes) {
            StdDraw.setPenColor(StdDraw.ORANGE);
            StdDraw.filledSquare(box.getX(), box.getY(), 15);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
            StdDraw.text(box.getX(), box.getY(), "+");
        }
        
        oguz.drawHealthBar();

        StdDraw.setPenColor(StdDraw.YELLOW);
        for (Bullet b : bullets) {
            StdDraw.filledCircle(b.getX(), b.getY(), 5);
        }

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(oguz.getTopLeftCornerX(), oguz.getTopLeftCornerY(), 3);
        StdDraw.filledCircle(oguz.getBottomRightCornerX(), oguz.getBottomRightCornerY(), 3);

        StdDraw.setPenColor(StdDraw.BLUE);
        for (Zombie z : zombies) {
            StdDraw.filledCircle(z.getTopLeftCornerX(), z.getTopLeftCornerY(), 3);
            StdDraw.filledCircle(z.getBottomRightCornerX(), z.getBottomRightCornerY(), 3);
        }

        StdDraw.show();
        StdDraw.pause(30);

        // Game Over kontrolü
        if (oguz.getHealth() <= 0) {
            long finalTime = (System.currentTimeMillis() - gameStartTime) / 1000;
            return showGameOver(score, finalTime);
        }
    }
}

boolean showGameOver(int finalScore, long finalTime) {
    boolean waiting = true;
    
    while (waiting) {
        StdDraw.clear(StdDraw.WHITE);
        
        StdDraw.setPenColor(new java.awt.Color(0, 0, 0, 150));
        StdDraw.filledRectangle(300, 300, 300, 300);
        
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 48));
        StdDraw.text(300, 450, "GAME OVER");
        
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        StdDraw.text(300, 370, "Score: " + finalScore);
        StdDraw.text(300, 320, "Süre: " + finalTime + "s");
        
        drawRetryButton();
        
        StdDraw.show();
        StdDraw.pause(30);
        
        if (StdDraw.isMousePressed()) {
            double mouseX = StdDraw.mouseX();
            double mouseY = StdDraw.mouseY();
            
            if (mouseX >= 200 && mouseX <= 400 && mouseY >= 200 && mouseY <= 250) {
                return true;
            }
        }
    }
    return false;
}

void drawRetryButton() {
    StdDraw.setPenColor(new java.awt.Color(0, 150, 0));
    StdDraw.filledRectangle(300, 225, 100, 25);
    
    StdDraw.setPenColor(StdDraw.WHITE);
    StdDraw.setPenRadius(0.01);
    StdDraw.rectangle(300, 225, 100, 25);
    
    StdDraw.setPenColor(StdDraw.WHITE);
    StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
    StdDraw.text(300, 225, "RETRY");
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

boolean isCollidingCharacterAmmoBox(Chararacter oguz, AmmoBox box) {
    if (oguz.getTopLeftCornerX() > box.getBottomRightCornerX() || box.getTopLeftCornerX() > oguz.getBottomRightCornerX()){
        return false;
    }

    if (oguz.getBottomRightCornerY() > box.getTopLeftCornerY() || box.getBottomRightCornerY() > oguz.getTopLeftCornerY() ){
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

void drawMenu(long gameStartTime, int score, double spawnRate, int ammo) {
    long elapsedTime = (System.currentTimeMillis() - gameStartTime) / 1000;

    StdDraw.setPenColor(new java.awt.Color(50, 50, 50));
    StdDraw.filledRectangle(300, 565, 300, 35);

    StdDraw.setPenColor(StdDraw.WHITE);
    StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
    StdDraw.text(75, 565, "Süre: " + elapsedTime + "s");
    StdDraw.text(250, 565, "Score: " + score);
    StdDraw.text(400, 565, "Ammo: " + ammo);
    StdDraw.text(520, 565, "Spawn: " + String.format("%.1f", spawnRate));
}
