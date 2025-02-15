import java.awt.*;
//Also the bullet behaiviour can be changed
public class Bullet {
    public int x, y;
    public int speed = 7;

    public Bullet(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public void move() { x -= speed; }

    public boolean isOffScreen() { return x < 0; }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, 20, 10);
    }
}
