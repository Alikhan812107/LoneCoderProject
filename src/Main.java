import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame("Lone Coder Platformer");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setSize(800, 600);
        window.setResizable(false);
        window.setLocationRelativeTo(null);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.setVisible(true);


        Thread gameThread = new Thread(() -> {
            while (true) {
                gamePanel.update();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.start();
    }
}
