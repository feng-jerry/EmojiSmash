package fengruijie.code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 冯睿杰 1800013065
 */

public class Main extends JFrame {

    boolean timerSwitch = false;

    public Main() {
        init();
    }

    private void init() {
        GamePanel panel = new GamePanel();

        add(panel);
        setVisible(true);

        setTitle("Emoji大作战");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false); // 固定窗体
        /* 窗体居中 */
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension size = new Dimension(Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);
        int width = toolkit.getScreenSize().width;
        int height = toolkit.getScreenSize().height;
        setBounds((int) (width - size.getWidth()) / 2, (int) (height - size.getHeight()) / 3,
                (int) size.getWidth(), (int) size.getHeight());
        setLocationRelativeTo(null);

        // 玩家键盘监听
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Keys.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                Keys.remove(e.getKeyCode());
            }
        });

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (timerSwitch) {
                    timer.cancel();
                    return;
                }
                panel.repaint();
                if (panel.gameOver || panel.win) {
                    Audio.BGM.clip.stop();
                    if (panel.gameOver)
                        Audio.GameOver.clip.start();
                    else if (panel.win)
                        Audio.Win.clip.start();
                    System.out.println("gg");
                    timerSwitch = true;
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 5);
    }

    public static void main(String[] args) {
        new Main();
    }
}