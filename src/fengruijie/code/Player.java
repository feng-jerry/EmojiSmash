package fengruijie.code;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Player extends BaseElement {

    boolean gameOver = false;
    int pushCount = 0, shineCount = 0;

    public Player(GamePanel panel) {
        super(panel, 50, 50);
        image = new ImageIcon(
                Objects.requireNonNull(
                        Player.class.getClassLoader().getResource(
                                Constant.RESOURCES_PATH + "player.png"))).getImage();
    }

    @Override
    void action() {
        if (!(Keys.LEFT.use() || Keys.RIGHT.use() || Keys.UP.use() || Keys.DOWN.use())) {
            xSpeed = 5;
            ySpeed = 5;
        }
        else {
            if (Keys.LEFT.use()) {
                x -= xSpeed;
                directionX = Direction.LEFT;
            } else if (Keys.RIGHT.use()) {
                x += xSpeed;
                directionX = Direction.RIGHT;
            }

            if (Keys.UP.use()) {
                y -= ySpeed;
                directionY = Direction.UP;
            } else if (Keys.DOWN.use()) {
                y += ySpeed;
                directionY = Direction.DOWN;
            }
        }
        checkXY();
        for (Enemy e: panel.enemies)
            if (intersects(e)) {
                gameOver = true;
                return;
            }

        int minDistance = Integer.MAX_VALUE;
        Enemy minDistanceEnemy = null;
        for (Enemy e: panel.enemies) {
            int d = distance(e);
            if (d < minDistance) {
                minDistance = d;
                minDistanceEnemy = e;
            }
        }

        if (Keys.PUSH.use() && pushCount == 0) {
            pushCount = Constant.PUSH_RECOVER_CYCLE;
            assert minDistanceEnemy != null;
            if (minDistanceEnemy.type == Enemy.Type.Sunglasses) {
                minDistanceEnemy.type = Enemy.Type.Common;
                minDistanceEnemy.image = new ImageIcon(
                        Objects.requireNonNull(
                                Player.class.getClassLoader().getResource(
                                        Constant.RESOURCES_PATH + "common.png"))).getImage();
            }
        }

        if (Keys.SHINE.use() && shineCount == 0) {
            shineCount = Constant.SHINE_RECOVER_CYCLE;
            assert minDistanceEnemy != null;
            if (minDistanceEnemy.type == Enemy.Type.Common) {
                minDistanceEnemy.type = Enemy.Type.Dizzy;
                minDistanceEnemy.dizzyCount = Constant.ENEMY_DIZZY_RECOVER_CYCLE;
                minDistanceEnemy.image = new ImageIcon(
                        Objects.requireNonNull(
                                Player.class.getClassLoader().getResource(
                                        Constant.RESOURCES_PATH + "dizzy.png"))).getImage();
            }
        }

        if (Keys.PULL.use())
            if (minDistanceEnemy != null) {
                if (minDistanceEnemy.type == Enemy.Type.Dizzy) {
                    int s = Math.abs(x - minDistanceEnemy.x) + Math.abs(y - minDistanceEnemy.y);
                    minDistanceEnemy.x += Math.round(3f * (x - minDistanceEnemy.x) / s);
                    minDistanceEnemy.y += Math.round(3f * (y - minDistanceEnemy.y) / s);
                }
                if (intersects(minDistanceEnemy))
                    panel.enemies.remove(minDistanceEnemy);
            }

        if (pushCount > 0)
            pushCount--;
        if (shineCount > 0)
            shineCount--;
    }

    @Override
    void drawImage(Graphics g) {
        super.drawImage(g);
        if (shineCount > 0) {
            String s = "眩晕技能冷却时间："
                    + new BigDecimal(shineCount * 0.005f).setScale(1, RoundingMode.HALF_UP).floatValue()
                    + "秒";
            Font font = new Font("Helvetica", Font.BOLD, 32);
            g.setColor(Color.red);
            g.setFont(font);
            g.drawString(s, 0, 50);
        }
        if (pushCount > 0) {
            String s = "冲击技能冷却时间："
                    + new BigDecimal(pushCount * 0.005f).setScale(1, RoundingMode.HALF_UP).floatValue()
                    + "秒";
            Font font = new Font("Helvetica", Font.BOLD, 32);
            g.setColor(Color.ORANGE);
            g.setFont(font);
            g.drawString(s, 0, 100);
        }
    }

    int distance(Enemy e) {
        int dx = x - e.x, dy = y - e.y;
        return dx * dx + dy * dy;
    }
}