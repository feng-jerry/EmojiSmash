package fengruijie.code;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public enum Keys {

    SPACE(KeyEvent.VK_SPACE),
    LEFT(KeyEvent.VK_A),
    RIGHT(KeyEvent.VK_D),
    UP(KeyEvent.VK_W),
    DOWN(KeyEvent.VK_S),
    SHINE(KeyEvent.VK_K),
    PULL(KeyEvent.VK_J),
    PUSH(KeyEvent.VK_L);

    private final static Set<Integer> keySet = new HashSet<>();

    Keys(int keyValue) {
        this.keyValue = keyValue;
    }

    private final int keyValue; // 键值

    public boolean use() {
        return keySet.contains(keyValue);
    }

    public static void add(int keyCode) {
        keySet.add(keyCode);
    }

    public static void remove(int keyCode) {
        keySet.remove(keyCode);
    }
}