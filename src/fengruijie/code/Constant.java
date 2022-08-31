package fengruijie.code;

public abstract class Constant {

    public final static int ELEMENT_SIZE = 48; // 素材原始尺寸
    public final static int FRAME_WIDTH = ELEMENT_SIZE * 20; // 主窗体宽
    public final static int FRAME_HEIGHT = ELEMENT_SIZE * 15; // 主窗体高

    public final static String RESOURCES_PATH = "fengruijie/resources/"; // 资源加载路径头

    public final static int SHINE_RECOVER_CYCLE = 400; // 眩晕敌人技能冷却周期数（0.5s）
    public final static int PUSH_RECOVER_CYCLE = 1000; // 冲击掉敌人墨镜技能冷却周期数
    public final static int ENEMY_DIZZY_RECOVER_CYCLE = 400; // 敌人眩晕恢复周期数
}