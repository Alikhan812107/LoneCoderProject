import java.awt.*;
import java.util.List;
//Enemy settings and variables which I can change
public class Enemy {
    public int x, y;
    public int speed = 3;
    private int minY, maxY;
    private int direction = 1;

    public Enemy(int startX, int startY, int minY, int maxY) {
        this.x = startX;
        this.y = startY;
        this.minY = minY;
        this.maxY = maxY;
    }
// Direction
    public void move() {
        y += speed * direction;
        if (y >= maxY || y <= minY) direction *= -1;
    }

    public void shoot(List<Bullet> bullets) {
        bullets.add(new Bullet(x, y + 10));
    }

    public void draw(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, 40, 40);
    }
}
