import java.awt.*;

public class Player {
    public int x, y;
    public int speed = 4;
    public int yVelocity = 0;
    public boolean onGround = false;
    private final int gravity = 1, jumpPower = 17;

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public void moveLeft() { x -= speed; }
    public void moveRight() { x += speed; }

    public void jump() {
        if (onGround) {
            yVelocity = -jumpPower;
            onGround = false;
        }
    }

    public void applyGravity() {
        if (!onGround) {
            yVelocity += gravity;
        } else {
            yVelocity = 0;
        }
        y += yVelocity;


        if (y > 470) {
            y = 470;
            onGround = true;
        }
    }

    public void landOnPlatform(int platformY) {
        y = platformY - 30;
        yVelocity = 0;
        onGround = true;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 30, 30);
    }
}
