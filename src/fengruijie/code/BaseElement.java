package fengruijie.code;

import java.awt.*;

public abstract class BaseElement {
    protected int x, y, width, height, xSpeed, ySpeed;
    Direction directionX, directionY;
    Image image;
    GamePanel panel;

    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    public BaseElement() {
        this.width = Constant.ELEMENT_SIZE;
        this.height = Constant.ELEMENT_SIZE;
        this.directionX = Direction.LEFT;
        this.directionY = Direction.UP;
    }

    public BaseElement(GamePanel panel, int x, int y) {
        this();
        this.panel = panel;
        this.x = x;
        this.y = y;
    }

    void action() {
        x += (directionX == Direction.LEFT)? -xSpeed: xSpeed;
        y += (directionY == Direction.UP)? -ySpeed: ySpeed;
        checkXY();
    }

    void checkXY() {
        x = Math.min(panel.getWidth() - Constant.ELEMENT_SIZE, x);
        x = Math.max(0, x);
        y = Math.min(panel.getHeight() - Constant.ELEMENT_SIZE, y);
        y = Math.max(0, y);
    }

    void drawImage(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }

    public <E extends BaseElement> boolean intersects(E element) {
        return getRectangle().intersects(element.getRectangle());
    }
}
