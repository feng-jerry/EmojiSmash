package fengruijie.code;

import javax.swing.*;
import java.util.Objects;
import java.util.Random;

public class Enemy extends BaseElement {

    public enum Type {
        Dizzy,
        Common,
        Sunglasses
    }

    Type type;
    int moveCount = 0, stopCount = 100, dizzyCount;

    Enemy(Type type, GamePanel panel, int x, int y) {
        super(panel, x, y);
        this.type = type;
        String fileName = null;
        switch (type) {
            case Dizzy:
                fileName = "dizzy";
                break;
            case Common:
                fileName = "common";
                break;
            case Sunglasses:
                fileName = "sunglasses";
                break;
            default:
                break;
        }
        image = new ImageIcon(
                Objects.requireNonNull(
                        Enemy.class.getClassLoader().getResource(
                                Constant.RESOURCES_PATH + fileName + ".png"))).getImage();
    }

    @Override
    void action() {
        final long CURRENT_TIME = System.currentTimeMillis();

        if (!(type == Type.Dizzy)) {
            Random random = new Random();

            if (moveCount != 0) {
                moveCount--;
                if (moveCount == 0)
                    stopCount = 25 + random.nextInt(100);

                xSpeed = random.nextInt(4);
                ySpeed = random.nextInt(4);
                for (Enemy e :
                        panel.enemies)
                    if (this != e && intersects(e)) {
                        directionX = (x < e.x) ? Direction.LEFT : Direction.RIGHT;
                        directionY = (y < e.y) ? Direction.UP : Direction.DOWN;
                    }
                super.action();
            } else if (stopCount != 0) {
                stopCount--;
                if (stopCount == 0) {
                    moveCount = 25 + random.nextInt(100);
                    directionX = (random.nextInt(2) == 0) ? Direction.LEFT : Direction.RIGHT;
                    directionY = (random.nextInt(2) == 0) ? Direction.UP : Direction.DOWN;
                }
            }
        }
        else {
            dizzyCount--;
            if (dizzyCount == 0) {
                type = Type.Common;
                image = new ImageIcon(
                        Objects.requireNonNull(
                                Enemy.class.getClassLoader().getResource(
                                        Constant.RESOURCES_PATH + "common.png"))).getImage();
            }
        }
    }
}
