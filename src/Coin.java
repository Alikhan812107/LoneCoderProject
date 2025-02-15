import java.awt.*;
import java.util.Random;

public class Coin {
    public int x, y;
    private static final int SIZE = 17;
    private static final Random random = new Random();

    public Coin() {
        this.x = random.nextInt(700) + 50;
        this.y = random.nextInt(400) + 50;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, SIZE, SIZE);
    }
}
