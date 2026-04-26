//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * Main game loop and initialization.
 */
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

/**
 * Starts and runs the main game loop. This method manages game entities such as
 * the character, zombies, bullets, ammo boxes, and health boxes. It handles user
 * input, spawns game objects, updates their states, detects collisions, and renders
 * the game scene.
 *
 * @param canvasWidth  the width of the game canvas in pixels
 * @param canvasHeight the height of the game canvas in pixels
 * @return true if the player chooses to restart the game after completing or losing;
 * false if the player exits the game.
 */
boolean runGame(int canvasWidth, int canvasHeight) {
    Chararacter oguz = new Chararacter();
    ArrayList<Zombie> zombies = new ArrayList<>();
    zombies.add(new Zombie());
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<AmmoBox> ammoBoxes = new ArrayList<>();
    ArrayList<HealthBox> healthBoxes = new ArrayList<>();

    long gameStartTime = System.currentTimeMillis();
    long lastSpawnTime = gameStartTime;
    long lastAmmoSpawnTime = gameStartTime;
    long lastHealthSpawnTime = gameStartTime;
    int score = 0;
    double spawnRate = 0.5;

    int keyboardPauseDuration = 100;
    boolean spacePressed = false;
    Random rand = new Random();

    // Game loop
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
        // Spawn rate increases gradually: starts at 0.5, increases by 0.3 every 15 seconds
        spawnRate = 0.5 + (elapsedSeconds / 15) * 0.3;

        // Zombie spawn
        // Calculate the time elapsed since the last zombie spawn
        long timeSinceLastSpawn = currentTime - lastSpawnTime;
        // Calculate the spawn interval based on the current spawn rate
        long spawnInterval = (long)(1000 / spawnRate);

        // Check if enough time has passed to spawn a new zombie
        if (timeSinceLastSpawn >= spawnInterval) {
            // Create and add a new zombie to the game
            zombies.add(new Zombie());
            // Update the last spawn time to current time for next spawn calculation
            lastSpawnTime = currentTime;
        }

        // HealthBox spawn
        long timeSinceLastHealthSpawn = currentTime - lastHealthSpawnTime;
        if (timeSinceLastHealthSpawn >= 5000) { // 7000 -> 5000
            if (rand.nextDouble() < 0.4) { // 0.25 -> 0.4 (%40 şans)
                int randomX = rand.nextInt(550) + 25;
                int randomY = rand.nextInt(250) + 150;
                healthBoxes.add(new HealthBox(randomX, randomY));
            }
            lastHealthSpawnTime = currentTime;
        }

        // AmmoBox spawn
        long timeSinceLastAmmoSpawn = currentTime - lastAmmoSpawnTime;
        if (timeSinceLastAmmoSpawn >= 4000) { // 5000 -> 4000
            if (rand.nextDouble() < 0.45) { // 0.3 -> 0.45 (%45 şans)
                int randomX = rand.nextInt(550) + 25;
                int randomY = rand.nextInt(250) + 150;
                ammoBoxes.add(new AmmoBox(randomX, randomY));
            }
            lastAmmoSpawnTime = currentTime;
        }

        // Character zombie collision
        for (Zombie z : zombies) {
            if (isCollidingCharacterZombie(oguz, z)) {
                oguz.loseHealth();
            }
        }

        // Character ammo box collision
        for (int i = 0; i < ammoBoxes.size(); i++) {
            AmmoBox box = ammoBoxes.get(i);
            if (isCollidingCharacterAmmoBox(oguz, box)) {
                oguz.addAmmo(box.getAmmoAmount());
                ammoBoxes.remove(i);
                i--;
            }
        }

        // Character health box collision
        for (int i = 0; i < healthBoxes.size(); i++) {
            HealthBox box = healthBoxes.get(i);
            if (isCollidingCharacterHealthBox(oguz, box)) {
                oguz.addHealth(box.getHealthAmount());
                healthBoxes.remove(i);
                i--;
            }
        }

// Iterate through all bullets and update their state
for (int i = 0; i < bullets.size(); i++) {
    Bullet b = bullets.get(i);
    b.move(); // Move the bullet upward on the screen
    
    boolean bulletHit = false; // Flag to track if the bullet hit something
    
    // Check if the bullet collides with any zombie
    for (int j = 0; j < zombies.size(); j++) {
        Zombie z = zombies.get(j);
        if (isCollidingBulletZombie(b, z)) {
            bulletHit = true; // Mark that the bullet has hit a target
            score += 10; // Increase player score
            zombies.remove(j); // Remove the zombie from the game
            j--; // Decrement index since we removed an element from the list
        }
    }
    
    // Check if the bullet collides with any health box
    for (int j = 0; j < healthBoxes.size(); j++) {
        HealthBox hb = healthBoxes.get(j);
        if (isCollidingBulletHealthBox(b, hb)) {
            bulletHit = true; // Mark that the bullet has hit a target
            healthBoxes.remove(j); // Remove the health box from the game
            j--; // Decrement index since we removed an element from the list
            break; // Exit loop since this bullet can only destroy one health box
        }
    }
    
    // Remove the bullet if it hit something or went off screen
    if (bulletHit) {
        bullets.remove(i); // Remove the bullet that hit a target
        i--; // Decrement index since we removed an element from the list
    } else if (b.isOffScreen()) {
        bullets.remove(i); // Remove the bullet that left the screen
        i--; // Decrement index since we removed an element from the list
    }
}

        // AmmoBox movement
        for (int i = 0; i < ammoBoxes.size(); i++) {
            AmmoBox box = ammoBoxes.get(i);
            if (box.isOffScreen()) {
                ammoBoxes.remove(i);
                i--;
            }
        }

        // Zombie movement
        for (int i = 0; i < zombies.size(); i++) {
            Zombie z = zombies.get(i);
            z.move();

            if (z.getY() < 0) {
                score -= 5;
                zombies.remove(i);
                i--;
            }
        }

        StdDraw.clear(StdDraw.WHITE);

        //Draw Menu
        drawMenu(gameStartTime, score, spawnRate, oguz.getAmmo(), oguz.getHealth());

        // Draw Oguz
        oguz.draw();

        // Draw Zombies
        for (Zombie z : zombies) {
            z.draw();
        }

        // Draw AmmoBox
        for (AmmoBox box : ammoBoxes) {
            box.draw();
        }

        // Draw Bullets
        for (Bullet b : bullets) {
            b.draw();
        }

        //Draw HealthBox
        for (HealthBox box : healthBoxes) {
            box.draw();
        }

        //Draw HealthBar
        oguz.drawHealthBar();

        StdDraw.show();
        StdDraw.pause(30);

        // Game Over check
        if (oguz.getHealth() <= 0) {
            long finalTime = (System.currentTimeMillis() - gameStartTime) / 1000;
            return showGameOver(score, finalTime);
        }
    }
    }

/**
 * Displays the "Game Over" screen with the final score and time, and provides an option to retry the game.
 * @param finalScore The final score achieved by the player.
 * @param finalTime The total time played by the player in seconds.
 * @return True if the player clicks the "Retry" button, false otherwise.
 */
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

/**
 * Draws the retry button on the screen.
 */
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

/**
 * Checks if a character collides with a zombie.
 * @param oguz The character object.
 * @param zombie The zombie object.
 * @return True if collision occurs, false otherwise.
 */
boolean isCollidingCharacterZombie(Chararacter oguz, Zombie zombie) {
    if (oguz.getTopLeftCornerX() > zombie.getBottomRightCornerX() || zombie.getTopLeftCornerX() > oguz.getBottomRightCornerX()){
        return false;
    }

    if (oguz.getBottomRightCornerY() > zombie.getTopLeftCornerY() || zombie.getBottomRightCornerY() > oguz.getTopLeftCornerY() ){
        return false;
    }
    return true;
}

/**
 * Checks if a character collides with an ammo box.
 * @param oguz The character object.
 * @param box The ammo box object.
 * @return True if collision occurs, false otherwise.
 */
boolean isCollidingCharacterAmmoBox(Chararacter oguz, AmmoBox box) {
    if (oguz.getTopLeftCornerX() > box.getBottomRightCornerX() || box.getTopLeftCornerX() > oguz.getBottomRightCornerX()){
        return false;
    }

    if (oguz.getBottomRightCornerY() > box.getTopLeftCornerY() || box.getBottomRightCornerY() > oguz.getTopLeftCornerY() ){
        return false;
    }
    return true;
}


/**
 * Checks if a character collides with a health box.
 * @param oguz The character object.
 * @param box The health box object.
 * @return True if collision occurs, false otherwise.
 */
boolean isCollidingCharacterHealthBox(Chararacter oguz, HealthBox box) {
    if (oguz.getTopLeftCornerX() > box.getBottomRightCornerX() || box.getTopLeftCornerX() > oguz.getBottomRightCornerX()){
        return false;
    }

    if (oguz.getBottomRightCornerY() > box.getTopLeftCornerY() || box.getBottomRightCornerY() > oguz.getTopLeftCornerY() ){
        return false;
    }
    return true;
}

/**
 * Checks if a bullet collides with a zombie.
 * @param bullet The bullet object.
 * @param zombie The zombie object.
 * @return True if collision occurs, false otherwise.
 */
boolean isCollidingBulletZombie(Bullet bullet, Zombie zombie) {
    if (bullet.getTopLeftCornerX() > zombie.getBottomRightCornerX() || zombie.getTopLeftCornerX() > bullet.getBottomRightCornerX()){
        return false;
    }

    if (bullet.getBottomRightCornerY() > zombie.getTopLeftCornerY() || zombie.getBottomRightCornerY() > bullet.getTopLeftCornerY() ){
        return false;
    }
    return true;
}

    /**
     * Checks if a bullet collides with a health box.
     * @param bullet The bullet object.
     * @param healthBox The health box object.
     * @return True if collision occurs, false otherwise.
     */
    boolean isCollidingBulletHealthBox(Bullet bullet, HealthBox healthBox) {
        if (bullet.getTopLeftCornerX() > healthBox.getBottomRightCornerX() || healthBox.getTopLeftCornerX() > bullet.getBottomRightCornerX()){
            return false;
        }

        if (bullet.getBottomRightCornerY() > healthBox.getTopLeftCornerY() || healthBox.getBottomRightCornerY() > bullet.getTopLeftCornerY() ){
            return false;
        }
        return true;
    }
/**
 * Draws the game menu with score, time, ammo, and spawn rate information.
 * @param gameStartTime The start time of the game in milliseconds.
 * @param score The current score of the player.
 * @param spawnRate The current spawn rate of zombies.
 * @param ammo The current ammo count of the player.
 */
void drawMenu(long gameStartTime, int score, double spawnRate, int ammo, int health) {
    long elapsedTime = (System.currentTimeMillis() - gameStartTime) / 1000;

    StdDraw.setPenColor(new java.awt.Color(50, 50, 50));
    StdDraw.filledRectangle(300, 565, 300, 35);

    StdDraw.setPenColor(StdDraw.WHITE);
    StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));

    StdDraw.text(50, 575, "Health: " + health);
    StdDraw.text(50, 555, "Ammo: " + ammo);

    StdDraw.text(550, 575, "Score: " + score);
    StdDraw.text(550, 555, "Spawn: " + String.format("%.1f", spawnRate));
}
