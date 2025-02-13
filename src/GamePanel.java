import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener {
    private int playerX = 50;
    private int playerY = 400;
    private int playerSpeed = 5;
    private int velocityY = 0;
    private boolean onGround = false;
    private final int gravity = 1;
    private final int jumpStrength = 20;  // Increased jump height

    private boolean[] keys = new boolean[256];  // Track key presses

    private ArrayList<Rectangle> platforms = new ArrayList<>();

    public GamePanel() {
        this.setFocusable(true);
        this.addKeyListener(this);

        // Add platforms
        platforms.add(new Rectangle(0, 500, 800, 50));  // Ground
        platforms.add(new Rectangle(200, 400, 100, 10));
        platforms.add(new Rectangle(400, 300, 100, 10));
        platforms.add(new Rectangle(600, 200, 100, 10));

        this.setBackground(Color.CYAN);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw platforms
        g.setColor(Color.DARK_GRAY);
        for (Rectangle platform : platforms) {
            g.fillRect(platform.x, platform.y, platform.width, platform.height);
        }

        // Draw the player
        g.setColor(Color.RED);
        g.fillRect(playerX, playerY, 50, 50);
    }

    public void update() {
        // Apply gravity if not on the ground
        if (!onGround) {
            velocityY += gravity;
        } else {
            velocityY = 0;
        }

        playerY += velocityY;
        onGround = false;

        // Collision with platforms
        for (Rectangle platform : platforms) {
            if (playerY + 50 >= platform.y && playerY + 50 <= platform.y + 10 &&
                    playerX + 50 > platform.x && playerX < platform.x + platform.width) {
                playerY = platform.y - 50;
                onGround = true;
            }
        }

        // Handle movement while jumping
        if (keys[KeyEvent.VK_LEFT]) playerX -= playerSpeed;
        if (keys[KeyEvent.VK_RIGHT]) playerX += playerSpeed;

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        if (e.getKeyCode() == KeyEvent.VK_UP && onGround) {
            velocityY = -jumpStrength;
            onGround = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
