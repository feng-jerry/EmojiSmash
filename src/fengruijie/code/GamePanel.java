package fengruijie.code;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class GamePanel extends JPanel {

    Player player;
    CopyOnWriteArrayList<Enemy> enemies;
    boolean inGame = false;
    boolean gameOver = false;
    boolean win = false;

    GamePanel() {
        player = new Player(this);
        enemies = new CopyOnWriteArrayList<>();
        enemies.add(new Enemy(Enemy.Type.Common, this,
                Constant.FRAME_WIDTH / 4, Constant.FRAME_HEIGHT / 3));
        enemies.add(new Enemy(Enemy.Type.Sunglasses, this,
                Constant.FRAME_WIDTH / 2, Constant.FRAME_HEIGHT / 3));
        enemies.add(new Enemy(Enemy.Type.Common, this,
                Constant.FRAME_WIDTH * 3 / 4, Constant.FRAME_HEIGHT / 3));
        enemies.add(new Enemy(Enemy.Type.Sunglasses, this,
                Constant.FRAME_WIDTH / 4, Constant.FRAME_HEIGHT * 2 / 3));
        enemies.add(new Enemy(Enemy.Type.Common, this,
                Constant.FRAME_WIDTH / 2, Constant.FRAME_HEIGHT * 2 / 3));
        enemies.add(new Enemy(Enemy.Type.Sunglasses, this,
                Constant.FRAME_WIDTH * 3 / 4, Constant.FRAME_HEIGHT * 2 / 3));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backGround = new ImageIcon(
                Objects.requireNonNull(
                        GamePanel.class.getClassLoader().getResource(
                                Constant.RESOURCES_PATH + "background.png"))).getImage();
        g.drawImage(backGround, 0, 0, getWidth(), getHeight(), this);

        if (!inGame) {
            for (BaseElement e : enemies) {
                e.drawImage(g);
            }
            player.drawImage(g);

            drawStartImage(g);

            if (Keys.SPACE.use()) {
                inGame = true;
                Audio.BGM.clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
        else {
            for (BaseElement e : enemies)
                e.action();
            player.action();

            for (BaseElement e : enemies) {
                e.drawImage(g);
                if (e.intersects(player))
                    gameOver = true;
            }
            player.drawImage(g);

            if (gameOver) {
                try {
                    Thread.sleep(2750);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                g.drawImage(new ImageIcon(
                        Objects.requireNonNull(
                                GamePanel.class.getClassLoader().getResource(
                                        Constant.RESOURCES_PATH + "lose.png"))).getImage(),
                        0, (getHeight() - getWidth() / 4) / 2, getWidth(), getWidth() / 4, null);
            }

            if (win) {
                try {
                    Thread.sleep(2750);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                g.drawImage(new ImageIcon(
                        Objects.requireNonNull(
                                GamePanel.class.getClassLoader().getResource(
                                        Constant.RESOURCES_PATH + "win.png"))).getImage(),
                        0, (getHeight() - getWidth() / 4) / 2, getWidth(), getWidth() / 4, null);
            }
            win = enemies.isEmpty();
        }
    }

    void drawStartImage(Graphics g) {
        g.setColor(new Color(0, 32, 48));
        g.fillRect(0, getHeight() / 2 - 50, getWidth(), 100);
        g.setColor(Color.white);
        g.drawRect(0, getHeight() / 2 - 50, getWidth(), 100);

        String s = "请按空格键开始游戏";
        Font font = new Font("Helvetica", Font.BOLD, 32);
        FontMetrics metrics = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(s, (getWidth() - metrics.stringWidth(s)) / 2, getHeight() / 2);
    }
}