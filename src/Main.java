import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        // Window creation
        JFrame window = new JFrame("Lone Coder Platformer");
        // Close the window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Window settings
        window.setSize(800, 600);
        window.setResizable(false);
        window.setLocationRelativeTo(null);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.setVisible(true);

// gameThraed which controlls the process of the permanent game update
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
