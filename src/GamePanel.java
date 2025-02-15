import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements KeyListener {
    private Player player;
    private Enemy enemy;
    private List<Bullet> bullets = new ArrayList<>();
    private List<Coin> coins = new ArrayList<>();
    private List<Platform> platforms = new ArrayList<>();
    private int score = 0;
    private boolean gameOver = false;
    private boolean gameWon = false;
    private final int WINNING_SCORE = 10; // Number of coins required to win

    private boolean[] keys = new boolean[256]; // Tracks multiple keys at once

    private Timer gameLoop, coinTimer, bulletTimer;

    public GamePanel() {
        this.setFocusable(true);
        this.addKeyListener(this);
        this.setBackground(Color.CYAN); // Set background color

        player = new Player(50, 400);
        enemy = new Enemy(700, 300, 200, 450);

        platforms.add(new Platform(0, 500, 800, 50));  // Ground
        platforms.add(new Platform(150, 450, 120, 15));
        platforms.add(new Platform(300, 400, 100, 15));
        platforms.add(new Platform(500, 350, 120, 15));
        platforms.add(new Platform(650, 300, 100, 15));
        platforms.add(new Platform(250, 250, 150, 15));
        platforms.add(new Platform(450, 200, 120, 15));

        gameLoop = new Timer(30, e -> update());
        gameLoop.start();

        coinTimer = new Timer(3000, e -> coins.add(new Coin())); // Generate coins every 3 seconds
        coinTimer.start();

        bulletTimer = new Timer(2000, e -> enemy.shoot(bullets)); // Enemy shoots every 2 seconds
        bulletTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameOver) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER!", 300, 250);
            return;
        }

        if (gameWon) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("YOU WIN!", 300, 250);
            return;
        }

        for (Platform platform : platforms) platform.draw(g);
        player.draw(g);
        enemy.draw(g);

        for (Coin coin : coins) coin.draw(g);
        for (Bullet bullet : bullets) bullet.draw(g);

        // 🔹 Draw score on the screen
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, 30);
    }

    public void update() {
        if (gameOver || gameWon) return;

        player.applyGravity();
        enemy.move();

        // 🔹 Check collision with platforms (prevent falling)
        player.onGround = false;
        for (Platform platform : platforms) {
            if (player.y + 30 >= platform.y && player.y + 30 <= platform.y + 10 &&
                    player.x + 30 > platform.x && player.x < platform.x + platform.width) {
                player.landOnPlatform(platform.y);
            }
        }

        // 🔹 Prevent player from leaving the screen (borders)
        if (player.x < 0) player.x = 0;
        if (player.x > 770) player.x = 770;
        if (player.y > 570) player.y = 570;

        bullets.removeIf(Bullet::isOffScreen);
        for (Bullet bullet : bullets) {
            bullet.move();
            if (player.x < bullet.x + 20 && player.x + 30 > bullet.x &&
                    player.y < bullet.y + 10 && player.y + 30 > bullet.y) {
                gameOver = true;
            }
        }

        // 🔹 Fix coin collection logic (increase score when collecting)
        coins.removeIf(coin -> {
            boolean collected = player.x + 30 > coin.x && player.x < coin.x + 20 &&
                    player.y + 30 > coin.y && player.y < coin.y + 20;
            if (collected) score++;  // Increase score when coin is collected
            return collected;
        });

        // 🔹 Check if player has won
        if (score >= WINNING_SCORE) gameWon = true;

        // 🔹 Handles moving left/right while jumping
        if (keys[KeyEvent.VK_LEFT]) player.moveLeft();
        if (keys[KeyEvent.VK_RIGHT]) player.moveRight();
        if (keys[KeyEvent.VK_UP]) player.jump();

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true; // Track key press
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false; // Track key release
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
